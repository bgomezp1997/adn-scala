package com.ceiba.controllers.formatter

import com.ceiba.driver.command.PatientCommand
import com.ceiba.model.dto.PatientDTO
import play.api.libs.json.{Format, Json}

trait FormatterController {

  implicit val patientDTOFormat: Format[PatientDTO] = Json.format[PatientDTO]

  implicit val patientCommandFormat: Format[PatientCommand] = Json.format[PatientCommand]

}
