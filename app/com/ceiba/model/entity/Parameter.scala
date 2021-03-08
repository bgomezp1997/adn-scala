package com.ceiba.model.entity

import com.ceiba.model.dto.{EpsDTO, ParameterDTO}
import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import play.api.libs.json.{JsPath, Writes}

case class Parameter(id: Option[Long], name: String, value: String, types: String)

object Parameter {

  implicit def parameterDTOToParameter(parameterDTO: ParameterDTO) = Parameter(parameterDTO.id,
    parameterDTO.name,
    parameterDTO.value,
    parameterDTO.types)

  implicit val parameterWrites: Writes[Parameter] =
    (JsPath \ "id").writeNullable[Long]
      .and((JsPath \ "name").write[String])
      .and((JsPath \ "value").write[String])
      .and((JsPath \ "types").write[String])(unlift(Parameter.unapply))

}
