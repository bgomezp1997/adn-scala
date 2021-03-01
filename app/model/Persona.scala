package model

import play.api.libs.functional.syntax.{toApplicativeOps, toFunctionalBuilderOps, unlift}
import play.api.libs.json.Reads.minLength
import play.api.libs.json.{JsPath, Reads, Writes}

import java.time.LocalDate

case class Persona(identification: Int, name: String, lastName: String, age: Int, dateBirth: Option[LocalDate])

object Persona {

  implicit val personaWrites: Writes[Persona] =
    (JsPath \ "identification").write[Int]
      .and((JsPath \ "name").write[String])
      .and((JsPath \ "lastName").write[String])
      .and((JsPath \ "age").write[Int])
      .and((JsPath \ "dateBirth").writeNullable[LocalDate])(unlift(Persona.unapply))

  implicit val personaReads: Reads[Persona] =
    (JsPath \ "identification").read[Int]
      .and((JsPath \ "name").read[String](minLength[String](3)))
      .and((JsPath \ "lastName").read[String](minLength[String](5)))
      .and((JsPath \ "age").read[Int](Reads.min(0) keepAnd Reads.max(20)))
      .and((JsPath \ "dateBirth").readNullable[LocalDate])(Persona.apply _)

  var list: List[Persona] = {
    List(
      Persona(
        1094964963,
        "Brian",
        "Gomez",
        23,
        Some(LocalDate.now())
      ),
      Persona(
        1094964965,
        "Carlos",
        "Gomez",
        26,
        Some(LocalDate.now())
      )
    )
  }

  def save(persona: Persona) = {
    list = list ::: List(persona)
  }

}
