package com.ceiba.models.entities

import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import play.api.libs.json.Reads.minLength
import play.api.libs.json.{JsPath, Reads, Writes}

case class Eps(nit: String, name: String, phone: String, email: String)

object Eps {

  implicit val epsWrites: Writes[Eps] =
    (JsPath \ "nit").write[String]
      .and((JsPath \ "name").write[String])
      .and((JsPath \ "phone").write[String])
      .and((JsPath \ "email").write[String])(unlift(Eps.unapply))

  implicit val epsReads: Reads[Eps] =
    (JsPath \ "nit").read[String]
      .and((JsPath \ "name").read[String](minLength[String](3)))
      .and((JsPath \ "phone").read[String](minLength[String](5)))
      .and((JsPath \ "email").read[String](minLength[String](8)))(Eps.apply _)

}
