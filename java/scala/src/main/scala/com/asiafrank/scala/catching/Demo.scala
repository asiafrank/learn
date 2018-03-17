package com.asiafrank.scala.catching

import java.io.{FileInputStream, FileOutputStream}
import java.text.{ParseException, SimpleDateFormat}
import java.util.Date

import scala.io.StdIn
import scala.util.{Failure, Success, Try}

/**
  * Demo
  *
  * Created at 26/1/2017.
  *
  * @author asiafrank
  */
object Demo {
  def main(args: Array[String]) {
    def parse(s: String): Date = new SimpleDateFormat("yyyy-MM-dd").parse(s)
    def parseDate = parse("2017-1-23")

    type PE = ParseException
    import scala.util.control.Exception._
    val date = catching(classOf[PE]) either parseDate fold(_ => new Date, identity)
    println(date)

    tryExample()

    cleanly(new FileInputStream("/data"))(_.close()){
      in => in.read()
    }
  }

  def tryExample(): Unit = {
    val dividend = Try(StdIn.readLine("Enter an Int that you'd like to divide:\n").toInt)
    val divisor = Try(StdIn.readLine("Enter an Int that you'd like to divide by:\n").toInt)
    val problem = dividend.flatMap(x => divisor.map(y => x / y))
    problem match {
      case Success(v) =>
        println("Result of " + dividend.get + "/" + divisor.get + " is: " + v)
      case Failure(e) =>
        println("You must've divided by zero or entered something that's not an Int. Try again!")
        println("Info from the exception: " + e.getMessage)
    }
    println(problem)
  }

  def cleanly[A, B](resource: => A)(cleanup: A => Unit)(code: A => B): Either[Exception, B] = {
    try {
      val r = resource
      try {Right(code(r))} finally {cleanup(r)}
    }
    catch {case e: Exception => Left(e)}
  }
}
