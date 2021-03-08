package com.ceiba.driver.command

import java.time.LocalDateTime

case class PatientCommand(identification: Long, name: Option[String], lastName: Option[String], creationDate: Option[LocalDateTime], email: Option[String], nitEps: Option[String], stratum: Option[Int])
