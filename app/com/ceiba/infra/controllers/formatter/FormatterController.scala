package com.ceiba.infra.controllers.formatter

import com.ceiba.application.command.PatientCommand
import com.ceiba.domain.model.dto.PatientDTO
import play.api.libs.json.{Format, Json}

trait FormatterController {

  implicit val patientDTOFormat: Format[PatientDTO] = Json.format[PatientDTO]

  implicit val patientCommandFormat: Format[PatientCommand] = Json.format[PatientCommand]

}
