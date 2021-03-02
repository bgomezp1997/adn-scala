package com.ceiba.models.entities

import java.time.LocalDate

case class Date(id: Long, appointmentDate: LocalDate, identificationPatient: Long, identificationDoctor: Long, price: Double)
