package com.ceiba.model.dto

import java.time.LocalDateTime

case class PatientDTO(identification: Long, name: Option[String], lastName: Option[String], creationDate: Option[LocalDateTime], email: Option[String], nitEps: Option[String], stratum: Option[Int])

