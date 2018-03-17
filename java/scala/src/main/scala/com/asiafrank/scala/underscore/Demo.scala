package com.asiafrank.scala.underscore

/**
  * Underscore Demo
  *
  * http://ananthakumaran.in/2010/03/29/scala-underscore-magic.html
  * http://stackoverflow.com/questions/8000903/what-are-all-the-uses-of-an-underscore-in-scala
  * http://danielwestheide.com/blog/2013/01/30/the-neophytes-guide-to-scala-part-11-currying-and-partially-applied-functions.html
  *
  * Created at 2017/2/12 0012.
  *
  * @author zhangxf
  */
object Demo {
  def matchTest(x: Int): String = x match {
    case 1 => "one"
    case 2 => "two"
    case _ => "anything other than one and two"
  }

  def main(args: Array[String]): Unit = {
    println(matchTest(1))

    val list = List(1, 2, 3)
    val str = list match {
      case List(1, _, _) => "a list with three element and the first element is 1"
      case List(_*)      => "a list with zero or more elements"
    }
    println(str)

    List(1,2,3,4,5).foreach(print(_)) // equals to "List(1,2,3,4,5).foreach(a => print(a))"
    println()

    val divide = List(1,2,3,4,5).reduceLeft(_/_) // equals to "val sum = List(1,2,3,4,5).reduceLeft((a, b) => a / b)"
    println(divide)

    def fun0 = {
      println("fun0 invoked")
      "fun0"
    }
    val funLike0 = fun0
    println(funLike0) // type is string

    def fun1 = {
      println("fun1 invoked")
      "fun1"
    }
    val funLike1 = fun1 _ // type is function
    println(funLike1)
    funLike1()

    println(minimumSize(1, Email("hello", "message", "John", "Amy")))
    println(maximumSize(8, Email("hello", "message", "John", "Amy")))
    println(constraint20(lt, Email("hello", "message", "John", "Amy")))
    println(constraint30(eq, Email("hello", "message", "John", "Amy")))
  }

  case class Email(subject: String, text: String, sender: String, recipient: String)
  type EmailFilter = Email => Boolean

  type IntPairPredicate  = (Int, Int) => Boolean
  def sizeConstraint(predicate: IntPairPredicate, n: Int, email: Email) = predicate(email.text.length, n)

  val gt: IntPairPredicate = _ > _
  val ge: IntPairPredicate = _ >= _
  val lt: IntPairPredicate = _ < _
  val le: IntPairPredicate = _ <= _
  val eq: IntPairPredicate = _ == _

  val minimumSize: (Int, Email) => Boolean = sizeConstraint(ge, _: Int, _: Email)
  val maximumSize: (Int, Email) => Boolean = sizeConstraint(le, _: Int, _: Email)

  val constraint20: (IntPairPredicate, Email) => Boolean = sizeConstraint(_: IntPairPredicate, 20, _: Email)
  val constraint30: (IntPairPredicate, Email) => Boolean = sizeConstraint(_: IntPairPredicate, 30, _: Email)
}
