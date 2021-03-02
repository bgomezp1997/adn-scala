package com.ceiba.models.entities

import com.ceiba.models.dtos.PatientDTO
import play.api.libs.functional.syntax.{toApplicativeOps, toFunctionalBuilderOps, unlift}
import play.api.libs.json.Reads.minLength
import play.api.libs.json.{JsPath, Reads, Writes}

import java.time.LocalDate

case class Patient(identification: Long, name: String, lastName: String, creationDate: LocalDate, email: String, nitEps: String, stratum: Int)

object Patient {

  implicit def patientDTOToPatient(patientDTO: PatientDTO) = Patient(patientDTO.identification,
    patientDTO.name,
    patientDTO.lastName,
    patientDTO.creationDate,
    patientDTO.email,
    patientDTO.nitEps,
    patientDTO.stratum)

  implicit val patientWrites: Writes[Patient] =
    (JsPath \ "identification").write[Long]
      .and((JsPath \ "name").write[String])
      .and((JsPath \ "lastName").write[String])
      .and((JsPath \ "creationDate").write[LocalDate])
      .and((JsPath \ "email").write[String])
      .and((JsPath \ "nitEps").write[String])
      .and((JsPath \ "stratum").write[Int])(unlift(Patient.unapply))

}
