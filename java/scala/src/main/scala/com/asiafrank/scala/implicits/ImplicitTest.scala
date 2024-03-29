package com.asiafrank.scala.implicits

/** This example uses a structure from abstract algebra to show
  * how implicit parameters work. A semigroup is an algebraic structure
  * on a set A with an (associative) operation, called add here,
  * that combines a pair of A's and returns another A.
  */
abstract class SemiGroup[A] {
  def add(x: A, y: A): A
}

/** A monoid is a semigroup with a distinguished element of A,
  * called unit, that when combined with any other element of A returns
  * that other element again.
  */
abstract class Monoid[A] extends SemiGroup[A] {
  def unit: A
}

object ImplicitTest extends App {

  /** To show how implicit parameters work, we first define monoids
    * for strings and integers. The implicit keyword indicates
    * that the corresponding object can be used implicitly, within this scope,
    * as a parameter of a function marked implicit.
    */
  implicit object StringMonoid extends Monoid[String] {
    def add(x: String, y: String): String = x concat y

    def unit: String = ""
  }

  implicit object IntMonoid extends Monoid[Int] {
    def add(x: Int, y: Int): Int = x + y

    def unit: Int = 0
  }

  /** This method takes a List[A] returns an A which represent the combined
    * value of applying the monoid operation successively across the whole list.
    * Making the parameter m implicit here means we only have to provide the xs parameter
    * at the call site, since if we have a List[A] we know what type A actually is and
    * therefore what type Monoid[A] is needed. We can then implicitly find whichever val
    * or object in the current scope also has that type and use that without needing to
    * specify it explicitly.
    */
  def sum[A](xs: List[A])(implicit m: Monoid[A]): A =
  if (xs.isEmpty) m.unit
  else m.add(xs.head, sum(xs.tail))

  /** Here we call sum twice, with only one parameter each time.
    * Since the second parameter of sum, m, is implicit its value is looked up in
    * the current scope, based on the type of monoid required in each case,
    * meaning both expressions can be fully evaluated.
    */
  println(sum(List(1, 2, 3))) // uses IntMonoid implicitly
  println(sum(List("a", "b", "c"))) // uses StringMonoid implicitly
}