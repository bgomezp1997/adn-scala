package com.ceiba.models.dtos

import com.ceiba.models.entities.Parameter
import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json.Reads.minLength
import play.api.libs.json.{JsPath, Reads}

case class ParameterDTO(id: Option[Long], name: String, value: String, types: String)

object ParameterDTO {

  implicit def parameterToParameterDTO(parameter: Parameter) = ParameterDTO(parameter.id,
    parameter.name,
    parameter.value,
    parameter.types)

  implicit val parameterReads: Reads[ParameterDTO] =
    (JsPath \ "id").readNullable[Long]
      .and((JsPath \ "name").read[String](minLength[String](4)))
      .and((JsPath \ "value").read[String](minLength[String](4)))
      .and((JsPath \ "types").read[String](minLength[String](4)))(ParameterDTO.apply _)

}
