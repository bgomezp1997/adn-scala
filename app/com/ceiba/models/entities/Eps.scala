package com.ceiba.models.entities

import com.ceiba.models.dtos.EpsDTO
import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import play.api.libs.json.{JsPath, Writes}

case class Eps(nit: String, name: String, phone: String, email: String)

object Eps {

  implicit def epsDTOToEps(epsDTO: EpsDTO) = Eps(epsDTO.nit,
    epsDTO.name,
    epsDTO.phone,
    epsDTO.email)

  implicit val epsWrites: Writes[Eps] =
    (JsPath \ "nit").write[String]
      .and((JsPath \ "name").write[String])
      .and((JsPath \ "phone").write[String])
      .and((JsPath \ "email").write[String])(unlift(Eps.unapply))

}
