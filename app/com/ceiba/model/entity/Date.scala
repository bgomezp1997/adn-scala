package com.ceiba.model.entity

import com.ceiba.model.dto.DateDTO
import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import play.api.libs.json.{JsPath, Writes}

import java.time.LocalDate

case class Date(id: Option[Long], appointmentDate: LocalDate, identificationPatient: Long, identificationDoctor: Long, price: Double)

object Date {

  implicit def dateDTOToDate(dateDTO: DateDTO) = Date(dateDTO.id,
    dateDTO.appointmentDate,
    dateDTO.identificationPatient,
    dateDTO.identificationDoctor,
    dateDTO.price)

  implicit val dateWrites: Writes[Date] =
    (JsPath \ "id").writeNullable[Long]
      .and((JsPath \ "appointmentDate").write[LocalDate])
      .and((JsPath \ "identificationPatient").write[Long])
      .and((JsPath \ "identificationDoctor").write[Long])
      .and((JsPath \ "price").write[Double])(unlift(Date.unapply))

}