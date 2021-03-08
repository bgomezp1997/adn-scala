package com.ceiba.domain.model.entity

import cats.data.Validated._
import cats.implicits._
import com.ceiba.domain.validation.ErrorModel
import com.ceiba.domain.validation.ValidationModel._

import java.time.LocalDateTime

final case class Patient(identification: Long, name: String, lastName: String, creationDate: LocalDateTime, email: String, nitEps: String, stratum: Int)

object Patient {
  type IdPatient = Long

  def validateCreatePatient(identification: IdPatient, name: Option[String], lastName: Option[String], creationDate: Option[LocalDateTime], email: Option[String], nitEps: Option[String], stratum: Option[Int]): Either[ErrorModel, Patient] =
    (toValidatedNel(identification),
      validateMandatory(name, "nombre obligatorio"),
      validateMandatory(lastName, "clave obligatorio"),
      validateMandatory(creationDate, "fecha de creación obligatorio"),
      validateMandatory(email, "email obligatorio"),
      validateMandatory(nitEps, "nit de EPS obligatorio"),
      validateMandatory(stratum, "estrato obligatorio")).mapN(Patient(_, _, _, _, _, _, _))

}


