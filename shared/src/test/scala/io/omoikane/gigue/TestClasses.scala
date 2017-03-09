package io.omoikane.gigue

object TestClasses {
  sealed trait Proposition
  final case class Fixed(statement: String)    extends Proposition
  final case class Variable(statement: String) extends Proposition

  sealed trait Expression
  final case class Conjunction(expressions: Vector[Expression]) extends Expression
  final case class Disjunction(expressions: Vector[Expression]) extends Expression
  final case class Atom(proposition: Proposition)               extends Expression
  final case class Negation(expression: Expression)             extends Expression

  sealed trait Foo
//  final case class Bar(xs: Vector[Int])           extends Foo
  final case class Qux(i: Int, d: Option[Double]) extends Foo
}
