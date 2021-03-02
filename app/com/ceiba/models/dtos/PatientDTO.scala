package com.ceiba.models.dtos

import com.ceiba.models.entities.Patient
import play.api.libs.functional.syntax.{toApplicativeOps, toFunctionalBuilderOps}
import play.api.libs.json.Reads.minLength
import play.api.libs.json.{JsPath, Reads}

import java.time.LocalDate

case class PatientDTO(identification: Long, name: String, lastName: String, creationDate: LocalDate, email: String, nitEps: String, stratum: Int)

object PatientDTO {

  implicit def patientToPatientDTO(patient: Patient) = PatientDTO(patient.identification,
    patient.name,
    patient.lastName,
    patient.creationDate,
    patient.email,
    patient.nitEps,
    patient.stratum)

  implicit val patientReads: Reads[PatientDTO] =
    (JsPath \ "identification").read[Long]
      .and((JsPath \ "name").read[String](minLength[String](2)))
      .and((JsPath \ "lastName").read[String](minLength[String](2)))
      .and((JsPath \ "creationDate").read[LocalDate])
      .and((JsPath \ "email").read[String](minLength[String](8)))
      .and((JsPath \ "nitEps").read[String](minLength[String](6)))
      .and((JsPath \ "stratum").read[Int](Reads.min(0) keepAnd Reads.max(10)))(PatientDTO.apply _)

}
