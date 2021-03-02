package com.ceiba.models.dtos

import java.time.LocalDate

case class DateDTO(id: Long, appointmentDate: LocalDate, identificationPatient: Long, identificationDoctor: Long, price: Double)
