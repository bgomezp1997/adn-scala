package com.ceiba.model.dto

import com.ceiba.model.entity.Eps
import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json.Reads.minLength
import play.api.libs.json.{JsPath, Reads}

case class EpsDTO(nit: String, name: String, phone: String, email: String)

object EpsDTO {

  implicit def epsToEpsDTO(eps: Eps) = EpsDTO(eps.nit,
    eps.name,
    eps.phone,
    eps.email)

  implicit val epsReads: Reads[EpsDTO] =
    (JsPath \ "nit").read[String]
      .and((JsPath \ "name").read[String](minLength[String](2)))
      .and((JsPath \ "phone").read[String](minLength[String](10)))
      .and((JsPath \ "email").read[String](minLength[String](10)))(EpsDTO.apply _)

}


