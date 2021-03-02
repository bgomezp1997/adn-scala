package com.ceiba.models.dtos

import com.ceiba.models.entities.Date
import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json.{JsPath, Reads}

import java.time.LocalDate

case class DateDTO(id: Option[Long], appointmentDate: LocalDate, identificationPatient: Long, identificationDoctor: Long, price: Double)

object DateDTO {

  implicit def doctorToDoctorDTO(date: Date) = DateDTO(date.id,
    date.appointmentDate,
    date.identificationPatient,
    date.identificationDoctor,
    date.price)

  implicit val dateReads: Reads[DateDTO] =
    (JsPath \ "id").readNullable[Long]
      .and((JsPath \ "appointmentDate").read[LocalDate])
      .and((JsPath \ "identificationPatient").read[Long])
      .and((JsPath \ "identificationDoctor").read[Long])
      .and((JsPath \ "price").read[Double])(DateDTO.apply _)

}