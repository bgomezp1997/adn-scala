package com.ceiba.models.dtos

import java.time.LocalDate

case class DoctorDTO(identification: Long, name: String, lastName: String, creationDate: LocalDate, email: String, specialty: String, professionalCardNumber: Int)
