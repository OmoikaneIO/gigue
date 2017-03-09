package io.omoikane.gigue

import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import io.omoikane.gigue.CanonicalJson.encodeCanonicalJson
import io.omoikane.gigue.TestClasses._
import org.scalatest.{FreeSpec, Matchers}

@SuppressWarnings(
  Array("org.wartremover.warts.Nothing",
        "org.wartremover.warts.AsInstanceOf",
        "org.wartremover.warts.Any",
        "org.wartremover.warts.MutableDataStructures"))
class JSONTest extends FreeSpec with Matchers {

  val verum: Expression           = Conjunction(Vector.empty[Expression])
  val falsum: Expression          = Disjunction(Vector.empty[Expression])
  val conjVerum: Expression       = Conjunction(Vector(verum))
  val conjVerumFalsum: Expression = Conjunction(Vector(verum, falsum))
  val disjFalsum: Expression      = Disjunction(Vector(conjVerumFalsum, falsum))
  val propP: Expression           = Atom(Fixed("P"))
  val propQ: Expression           = Atom(Variable("Q"))
  val fancyP: Expression          = Disjunction(Vector(Conjunction(Vector(propP, Negation(propQ))), Conjunction(Vector(propP, propQ))))
  val fancyQ: Expression          = Disjunction(Vector(Conjunction(Vector(propQ)), Conjunction(Vector(propP, propQ))))
  val fancyFalsum: Expression = Disjunction(
    Vector(Conjunction(Vector(propP, Negation(propQ), propQ)), Conjunction(Vector(propP, Negation(propP), propQ))))

  val alphaIdentityMap: Map[String, String] = ('a' to 'z').map((c: Char) => (c.toString, c.toString)).toMap

  "Json" - {
    "conjVerum" in {
      decode[Expression](conjVerum.asJson.noSpaces) should be(Right(conjVerum))
    }

    "Foo/Qux" in {
      val foo: Foo = Qux(13, Some(14.0))
      decode[Foo](foo.asJson.noSpaces) should be(Right(foo))
    }

    "Alphabetical Identity Map" in {
      decode[Map[String, String]](alphaIdentityMap.asJson.noSpaces) should be(Right(alphaIdentityMap))
    }

    "fancyP" in {
      decode[Expression](propP.asJson.noSpaces) should be(Right(propP))
    }
  }

  "canonicalJson" - {
    "Vector[Int]" in {
      val ints: Vector[Int] = Vector(1, 2, 3)
      decode[Vector[Int]](encodeCanonicalJson(ints).noSpaces) should be(Right(ints))
    }

    "conjVerum" in {
      decode[Expression](encodeCanonicalJson(conjVerum).noSpaces) should be(Right(conjVerum))
    }

    "conjVerumFalsum" in {
      decode[Expression](encodeCanonicalJson(conjVerumFalsum).noSpaces) should be(Right(conjVerumFalsum))
    }

    "disjFalsum" in {
      decode[Expression](encodeCanonicalJson(disjFalsum).noSpaces) should be(Right(disjFalsum))
    }

    "fancyP" in {
      decode[Expression](encodeCanonicalJson(fancyP).noSpaces) should be(Right(fancyP))
    }

    "fancyQ" in {
      decode[Expression](encodeCanonicalJson(fancyQ).noSpaces) should be(Right(fancyQ))
    }

    "fancyFalsum" in {
      decode[Expression](encodeCanonicalJson(fancyFalsum).noSpaces) should be(Right(fancyFalsum))
    }

    "Foo/Qux" in {
      val foo: Foo = Qux(13, Some(14.0))
      decode[Foo](encodeCanonicalJson(foo).noSpaces) should be(Right(foo))
    }

    "Alphabetical Identity Map" in {
      decode[Map[String, String]](encodeCanonicalJson(alphaIdentityMap).noSpaces) should be(Right(alphaIdentityMap))
    }

    "Alphabetical Identity Map is sorted" in {
      encodeCanonicalJson(alphaIdentityMap).noSpaces should be(
        s"""{"a":"a","b":"b","c":"c"
           |,"d":"d","e":"e","f":"f"
           |,"g":"g","h":"h","i":"i"
           |,"j":"j","k":"k","l":"l"
           |,"m":"m","n":"n","o":"o"
           |,"p":"p","q":"q","r":"r"
           |,"s":"s","t":"t","u":"u"
           |,"v":"v","w":"w","x":"x"
           |,"y":"y","z":"z"}""".stripMargin.filter(_ >= ' '))
    }

  }
}
