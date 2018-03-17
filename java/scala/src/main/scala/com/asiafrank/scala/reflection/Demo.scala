package com.asiafrank.scala.reflection

import java.lang.reflect.Modifier

import com.asiafrank.scala.example.ScalaTest

import scala.reflect.runtime.{universe => ru}
import org.reflections.ReflectionUtils._

/**
  * Demo
  *
  * Created at 28/1/2017.
  *
  * @author asiafrank
  */
object Demo {
  def main(args: Array[String]) {
    val u = new User
    u.setId(1L)
    u.setUsername("Frank")
    u.setPassword("Frank")
    val userTypeTag = getTypeTag(u)
    val userType = userTypeTag.tpe
    println(userType)
    // get members
    println(userType.members)

    // get fields those are have setters and getters, and convert to sql column name style
    val setters = userType.members.collect({
      case m: ru.MethodSymbol if m.isSetter =>
        val name = m.name.decodedName.toString
        toColumnName(name)
    }).toSet

    val getters = userType.members.collect({
      case m: ru.MethodSymbol if m.isGetter =>
        val name = m.name.decodedName.toString
        toColumnName(name)
    }).toSet

    val columns = getters.intersect(setters)
    println(columns)

    val clazz = classOf[User]
    val methods = getAllMethods(clazz, withModifier(Modifier.PUBLIC))
    methods.forEach(m => println(m.getName))

    methods.forEach(m => {
      val methodName = m.getName
      val returnType = m.getReturnType
      println(s"return type: ${returnType.getName}")
      println(s"$methodName isString: ${returnType == classOf[String]}")
      println(s"$methodName isLong: ${returnType == classOf[Long]}")
      println(s"$methodName isJLong: ${returnType == classOf[java.lang.Long]}")
    })

    //================================
    // java return type check
    //================================

    println("------- java return type check --------")

    val methods1 = getAllMethods(classOf[ScalaTest])
    // check one: java primitive type = scala type, java Object type != scala type
    // don't use `match`, `match` is not work
    methods1.forEach(m => {
      val returnType = m.getReturnType
      if (classOfJByte == returnType)
        println(s"${m.getName} is JByte")
      else if (classOfJShort == returnType)
        println(s"${m.getName} is JShort")
      else if (classOfJInt == returnType)
        println(s"${m.getName} is JInt")
      else if (classOfJLong == returnType)
        println(s"${m.getName} is JLong")
      else if (classOfByte == returnType)
        println(s"${m.getName} is scala Byte")
      else if (classOfBytes == returnType)
        println(s"${m.getName} is Byte Array")
      else if (classOfShort == returnType)
        println(s"${m.getName} is scala Short")
      else if (classOfShorts == returnType)
        println(s"${m.getName} is Short Array")
      else if (classOfInt == returnType)
        println(s"${m.getName} is scala Int")
      else if (classOfInts == returnType)
        println(s"${m.getName} is Int Array")
      else if (classOfLong == returnType)
        println(s"${m.getName} is scala Long")
      else if (classOfLongs == returnType)
        println(s"${m.getName} is Long Array")
      else if (classOfString == returnType)
        println(s"${m.getName} is String")
      else
        println(s"${m.getName} is nothing")
    })

    println("---------------")

    // check two: check by string
    methods1.forEach(m => {
      m.getReturnType match {
        case strJByte    => println(s"${m.getName} is ${strJByte}")
        case strJByteObj => println(s"${m.getName} is ${strJByteObj}")
        case strJInt     => println(s"${m.getName} is ${strJInt}")
        case strJIntObj  => println(s"${m.getName} is ${strJIntObj}")
        case strJLong    => println(s"${m.getName} is ${strJLong}")
        case strJLongObj => println(s"${m.getName} is ${strJLongObj}")
        case strJString  => println(s"${m.getName} is ${strJString}")
        case _           => println(s"${m.getName} is nothing")
      }
    })
  }

  val columnRegEx = "([a-z])([A-Z]+)"
  val replacement = "$1_$2"

  def getTypeTag[T: ru.TypeTag](obj: T) = ru.typeTag[T]

  // `createdAt` to `created_at`
  def toColumnName(s: String): String = {
    val end = s.indexOf("_")
    if (end != -1) {
      val rs = s.substring(0, end).replaceAll(columnRegEx, replacement).toLowerCase
      rs
    } else {
      val rs = s.replaceAll(columnRegEx, replacement).toLowerCase
      rs
    }
  }

  val classOfJByte = classOf[java.lang.Byte]
  val classOfJShort = classOf[java.lang.Short]
  val classOfJInt = classOf[java.lang.Integer]
  val classOfJLong = classOf[java.lang.Long]

  val classOfByte = classOf[Byte]
  val classOfBytes = classOf[Array[Byte]]
  val classOfShort = classOf[Short]
  val classOfShorts = classOf[Array[Short]]
  val classOfInt = classOf[Int]
  val classOfInts = classOf[Array[Int]]
  val classOfLong = classOf[Long]
  val classOfLongs = classOf[Array[Long]]
  val classOfString = classOf[String]

  val strJVoid = "void"
  val strJByte = "byte"
  val strJByteObj = "java.lang.Byte"
  val strJShort = "short"
  val strJShortObj = "java.lang.Short"
  val strJInt = "int"
  val strJIntObj = "java.lang.Integer"
  val strJLong = "long"
  val strJLongObj = "java.lang.Long"
  val strString = "java.lang.String"
}
