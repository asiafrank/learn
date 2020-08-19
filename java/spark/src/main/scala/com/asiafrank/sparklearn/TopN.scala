package com.asiafrank.sparklearn

import java.util.concurrent.TimeUnit

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object TopN {

  // 方法一：
  // 1. map+groupByKey：(year,month) 作为key进行分组
  // 2. mapValues：
  //    a. HashMap去重，同一天取高的那个值
  //    b. HashMap转成 List<(Int, Int)>，然后按温度倒序排序
  def method1(data: RDD[(Int, Int, Int, Int)]): Unit = {
    val grouped = data.map(t4 => ((t4._1, t4._2), (t4._3, t4._4))).groupByKey()
    val res: RDD[((Int, Int), List[(Int, Int)])] = grouped.mapValues(arr => {
      val map = new mutable.HashMap[Int, Int]()
      arr.foreach(x => {
        if (map.getOrElse(x._1, 0) < x._2) map.put(x._1, x._2)
      })
      map.toList.sorted(new Ordering[(Int, Int)] {
        override def compare(x: (Int, Int), y: (Int, Int)) = y._2.compareTo(x._2)
      })
    })
    res.foreach(println)
    //    ((2018,3),List((11,18)))
    //    ((2019,5),List((21,33)))
    //    ((2018,4),List((23,22)))
    //    ((1970,8),List((8,32), (23,23)))
    //    ((2019,6),List((1,39), (2,31)))
  }

  // 方法二：
  // 1. map+reduceByKey：以 (year,month,day)作为key，用 reduceByKey 直接取最大的那个值（去重）。得到“每天最高温”
  // 2. map：从“每天最高温”映射为(year,month)为key，(day,温度)为value
  // 3. groupBykey：第2步中的结果，以(year,month)为key做分组，value 以 Iterable 形式返回
  // 4. mapValues：第3步中的结果，其 value 做排序，取两条记录即可
  def method2(data: RDD[(Int, Int, Int, Int)]): Unit = {
    val reduced = data.map(t4 => ((t4._1, t4._2, t4._3), t4._4))
      .reduceByKey(
        (x: Int, y: Int) =>
          if (y > x)
            y
          else
            x
      )
    val mapped = reduced.map(t2 => ((t2._1._1, t2._1._2), (t2._1._3, t2._2)))
    val grouped = mapped.groupByKey()
    val result = grouped.mapValues(iter => iter.toList.sorted.take(2))
    result.foreach(println)
    //    ((2018,3),List((11,18)))
    //    ((2019,5),List((21,33)))
    //    ((2018,4),List((23,22)))
    //    ((1970,8),List((8,32), (23,23)))
    //    ((2019,6),List((1,39), (2,31)))
  }

  // 方法三：
  // 1. sortedBy：先按(year,month,温度)倒序排序（避免内存溢出）
  // 2. map+reducedByKey：映射为key=(year,month,day)，value=(温度)。再reduceByKey取最高温（去重）
  // 3. map+groupByKey：映射为key=(year,month)，value=(day,温度)。再 groupByKey
  def method3(data: RDD[(Int, Int, Int, Int)]): Unit = {
    val sorted = data.sortBy(t4 => (t4._1, t4._2, t4._4), ascending = false)
    val reduced = sorted.map(t4 => ((t4._1, t4._2, t4._3), t4._4))
      .reduceByKey( // 在这里丢失了顺序
        (x: Int, y: Int) =>
          if (y > x)
            y
          else
            x
      )
    val mapped = reduced.map(t2 => ((t2._1._1, t2._1._2), (t2._1._3, t2._2)))
    val grouped = mapped.groupByKey()
    grouped.foreach(println)
    // 输出：其结果不正确，它丢失了顺序
    //    ((2018,3),CompactBuffer((11,18)))
    //    ((2019,5),CompactBuffer((21,33)))
    //    ((2018,4),CompactBuffer((23,22)))
    //    ((1970,8),CompactBuffer((23,23), (8,32)))
    //    ((2019,6),CompactBuffer((2,31), (1,39)))
  }

  // 方法四：修复方法三种顺序丢失的问题
  //  1. sorted：先按(year,month,温度)倒序排序（避免内存溢出）
  //  2. map+groupByKey：映射为key=(year,month)，value=(day,温度)。再groupByKey，由于 key 和 第1步一样，顺序不会丢
  def method4(data: RDD[(Int, Int, Int, Int)]): Unit = {
    val sorted = data.sortBy(t4 => (t4._1, t4._2, t4._4), ascending = false)
    val grouped = sorted.map(t4 => ((t4._1, t4._2), (t4._3, t4._4))).groupByKey()
    grouped.foreach(println)
    // 输出：
    //    ((2018,3),CompactBuffer((11,18)))
    //    ((2019,5),CompactBuffer((21,33)))
    //    ((2018,4),CompactBuffer((23,22)))
    //    ((1970,8),CompactBuffer((8,32), (23,23)))
    //    ((2019,6),CompactBuffer((1,39), (1,38), (2,31)))
  }

  // 方法五：
  // 分布式计算的核心思想：调优天下无敌：combineByKey
  // 分布式是并行的，离线批量计算有个特征就是后续步骤(stage)依赖其一步骤(stage)
  // 如果前一步骤(stage)能够加上正确的combineByKey
  // 我们自定的combineByKey的函数，是尽量压缩内存中的数据
  //
  // 1. map：映射为key=(year,month)，value=(day,温度)
  // 2. combineByKey：
  //    a. createCombiner: 第一条记录的 value，key和value的格式是什么，也就是怎么放——value为一个3元素的Array
  //    b. mergeValue：如果有第二条记录，第二条以及以后的value怎么放进Array里——先去重后排序
  //    c. mergeCombiners：合并将结果写入磁盘时调用的函数——Array间的去重以及排序
  def method5(data: RDD[(Int, Int, Int, Int)]): Unit = {
    // 为了让排序倒序
    implicit val descSortOrder = new Ordering[(Int, Int)] {
      override def compare(x: (Int, Int), y: (Int, Int)) = y._2.compareTo(x._2)
    }

    val kv: RDD[((Int, Int), (Int, Int))] = data.map(t4 => ((t4._1, t4._2), (t4._3, t4._4)))
    val res: RDD[((Int, Int), Array[(Int, Int)])] = kv.combineByKey(
      //第一条记录怎么放：构造了value是个3元素Array的数据结构
      (v1: (Int, Int)) => {
        Array(v1, (0, 0), (0, 0))
      },
      //第二条，以及后续的怎么放：一条一条数据过来时，先去重后排序
      (oldv: Array[(Int, Int)], newv: (Int, Int)) => {
        var needAppend = true // true, 需要追加到第三个元素中;false，不需要追加

        // 去重，如果日期相同，则丢弃温度小的那一个
        // 如果日期不同，则追加到第三个元素中
        for (i <- oldv.indices) {
          if (oldv(i)._1 == newv._1) { // 如果日期有相同的
            needAppend = false // 日期相同，则无需追加
            if (oldv(i)._2 < newv._2) {
              oldv(i) = newv // 比当前元素大，替换这个元素
            }
          }
        }

        if (needAppend) { // 追加到第3个元素中
          oldv(oldv.length - 1) = newv
        }

        // 排序
        scala.util.Sorting.quickSort(oldv)
        oldv
      },
      (v1: Array[(Int, Int)], v2: Array[(Int, Int)]) => {
        // 为了简单，这里省略了去重逻辑
        val union: Array[(Int, Int)] = v1.union(v2)
        union.sorted
      }
    )
    res.map(x => (x._1, x._2.toList)).foreach(println)
// 输出：
//    ((2018,3),List((11,18), (0,0), (0,0)))
//    ((2019,5),List((21,33), (0,0), (0,0)))
//    ((2018,4),List((23,22), (0,0), (0,0)))
//    ((1970,8),List((8,32), (23,23), (0,0)))
//    ((2019,6),List((1,39), (2,31), (0,0)))
  }


  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("topN")
    val sc = new SparkContext(conf)

    val file: RDD[String] = sc.textFile("data/tqdata")
    //2019-6-1	39
    // 数据准备：
    // 1. map：\t 制表符切割成日期和温度
    // 2. map：日期再做处理，分割为(year,month,day,温度)的 Tuple
    val data = file.map(line => line.split("\t")).map(arr => {
      val arrs: Array[String] = arr(0).split("-")
      //(year,month,day,wd)
      (arrs(0).toInt, arrs(1).toInt, arrs(2).toInt, arr(1).toInt)
    })

    // method1(data)
    // method2(data)
    // method3(data)
    // method4(data)
    method5(data)


    TimeUnit.HOURS.sleep(1L)
  }
}
