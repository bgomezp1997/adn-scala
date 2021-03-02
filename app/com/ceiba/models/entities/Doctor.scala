package com.ceiba.models.entities

import java.time.LocalDate

case class Doctor(identification: Long, name: String, lastName: String, creationDate: LocalDate, email: String, specialty: String, professionalCardNumber: Int)
