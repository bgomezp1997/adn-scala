package com.ceiba.models.dtos

import com.ceiba.models.entities.Doctor
import play.api.libs.functional.syntax.{toApplicativeOps, toFunctionalBuilderOps}
import play.api.libs.json.Reads.minLength
import play.api.libs.json.{JsPath, Reads}

import java.time.LocalDate

case class DoctorDTO(identification: Long, name: String, lastName: String, creationDate: LocalDate, email: String, specialty: String, professionalCardNumber: Int)

object DoctorDTO {

  implicit def doctorToDoctorDTO(doctor: Doctor) = DoctorDTO(doctor.identification,
    doctor.name,
    doctor.lastName,
    doctor.creationDate,
    doctor.email,
    doctor.specialty,
    doctor.professionalCardNumber)

  implicit val doctorReads: Reads[DoctorDTO] =
    (JsPath \ "identification").read[Long]
      .and((JsPath \ "name").read[String](minLength[String](2)))
      .and((JsPath \ "lastName").read[String](minLength[String](2)))
      .and((JsPath \ "creationDate").read[LocalDate])
      .and((JsPath \ "email").read[String](minLength[String](10)))
      .and((JsPath \ "specialty").read[String](minLength[String](10)))
      .and((JsPath \ "professionalCardNumber").read[Int](Reads.min(0) keepAnd Reads.max(12)))(DoctorDTO.apply _)

}