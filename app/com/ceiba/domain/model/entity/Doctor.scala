package com.ceiba.domain.model.entity

import com.ceiba.domain.model.dto.DoctorDTO
import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import play.api.libs.json.{JsPath, Writes}

import java.time.LocalDate

case class Doctor(identification: Long, name: String, lastName: String, creationDate: LocalDate, email: String, specialty: String, professionalCardNumber: Int)

object Doctor {

  implicit def dateDTOToDate(doctorDTO: DoctorDTO) = Doctor(doctorDTO.identification,
    doctorDTO.name,
    doctorDTO.lastName,
    doctorDTO.creationDate,
    doctorDTO.email,
    doctorDTO.specialty,
    doctorDTO.professionalCardNumber)

  implicit val doctorWrites: Writes[Doctor] =
    (JsPath \ "identification").write[Long]
      .and((JsPath \ "name").write[String])
      .and((JsPath \ "lastName").write[String])
      .and((JsPath \ "creationDate").write[LocalDate])
      .and((JsPath \ "email").write[String])
      .and((JsPath \ "specialty").write[String])
      .and((JsPath \ "professionalCardNumber").write[Int])(unlift(Doctor.unapply))

}