package com.asiafrank.scala.expt {

  import java.io.{FileNotFoundException, FileReader, IOException}

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
      try {
        val f = new FileReader("input.txt")
        var s = f.read()
        while (s != -1) {
          print(s.toChar)
          s = f.read()
        }
      } catch {
        case e: FileNotFoundException =>
          println("Missing file exception")
        case e: IOException =>
          println("IO Exception")
      } finally {
        println("Exiting finally")
      }
    }
  }
}

