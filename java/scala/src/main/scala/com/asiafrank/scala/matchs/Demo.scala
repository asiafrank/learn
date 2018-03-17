package com.asiafrank.scala.matchs {

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
      println(matchTest0(1))

      println(matchTest1("two"))
      println(matchTest1("test"))
      println(matchTest1(1))

      val alice = Person("Alice", 25)
      val bob = Person("Bob", 32)
      val charlie = Person("Charlie", 32)

      for (p <- List(alice, bob, charlie)) {
        p match {
          case Person("Alice", 25) => println("Hi Alice!")
          case Person("Bob", 32)   => println("Hi Bob!")
          case Person(name, age)   => println(s"Age $age year, name: $name?")
        }
      }

      val o: Obj = null
      o match {
        case t: Obj => println(o) // not executed, because `t` must be instance
        case _      => println("other object")
      }
    }

    def matchTest0(x: Int): String = x match {
      case 1 => "one"
      case 2 => "two"
      case _ => "many"
    }

    def matchTest1(x: Any): Any = x match {
      case 1      => "one"
      case "two"  => 2
      case y: Int => "scala.Int"
      case _      => "many"
    }
  }

  case class Person(name: String, age: Int)

  class Obj {}
}