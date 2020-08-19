package com.asiafrank.flinklearn

import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.scala._

object WordCount {
  def main(args: Array[String]): Unit = {
    //准备环境
    /**
     * createLocalEnvironment 创建一个本地执行的环境  local
     * createLocalEnvironmentWithWebUI 创建一个本地执行的环境  同时还开启Web UI的查看端口  8081
     * getExecutionEnvironment 根据你执行的环境创建上下文，比如local  cluster
     */
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val stream: DataStream[String] = env.socketTextStream("node01", 8888)
    val wordStream = stream.flatMap(_.split(" "))
    val pairStream = wordStream.map((_,1))
    val keyByStream = pairStream.keyBy(0)
    val restStream = keyByStream.sum(1)
    restStream.print()
    //启动 flink 任务
    env.execute("first flink job")
  }
}
