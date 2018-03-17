package com.asiafrank.scala.io

import java.io._

import scala.io.StdIn

/**
  * Demo
  * <p>
  * </p>
  * Created at 1/20/2017.
  *
  * @author zhangxf
  */
object Demo {
  def main(args: Array[String]): Unit = {
    // write file
    val f = new File("test.txt")
    val writer = new PrintWriter(f)
    writer.write("Hello Scala")
    writer.close()

    // read file
    val fi = new FileInputStream(f)
    val ir = new InputStreamReader(fi)
    val reader = new BufferedReader(ir)
    var s: String = null
    /* do not read like this, (s = reader.readLine()) will become another object ()
    while ((s = reader.readLine()) != null) {
      println(s)
    }
    */
    s = reader.readLine()
    while (s != null) {
      println(s)
      s = reader.readLine()
    }
    reader.close()

    // read string from console
    print("Please enter your input:")
    val line = StdIn.readLine()
    println(s"Thanks, you just typed: $line")
  }
}
