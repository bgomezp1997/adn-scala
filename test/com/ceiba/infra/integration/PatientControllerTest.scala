package com.ceiba.infra.integration

import com.ceiba.application.command.PatientCommand
import com.ceiba.infra.config.AppTestKit
import com.ceiba.infra.controllers.formatter.FormatterController
import org.scalatest.MustMatchers
import play.api.test.FakeRequest
import play.api.test.Helpers._

import java.time.LocalDateTime

class PatientControllerTest extends AppTestKit with MustMatchers with FormatterController {

  // evaluar el contenido del mensaje
  "PatientController" can {
    "Guardar paciente" when {
      "Se invoca el servicio" in {
        val patientCommand = PatientCommand(109400001L, Some("Brian"), Some("Gómez"), Some(LocalDateTime.now()), Some("brian@mail.com"), Some("1"), Some(4))
        val request = FakeRequest(POST, "/patient").withJsonBody(patientCommandFormat.writes(patientCommand))

        val response = route(app, request).get

        status(response) mustBe (OK)
        val jsonContent = contentAsJson(response)
        (jsonContent \ "respuesta").get.toString mustBe "109400001"
      }

    }

    "Actualizar paciente" when {
      "Se invoca el servicio" in {
        val patientCommand = PatientCommand(109400001L, Some("Carlos"), Some("Gómez"), Some(LocalDateTime.now()), Some("carlos@mail.com"), Some("1"), Some(3))
        val request = FakeRequest(PUT, "/patient").withJsonBody(patientCommandFormat.writes(patientCommand))

        val response = route(app, request).get

        status(response) mustBe (OK)
        val jsonContent = contentAsJson(response)
        (jsonContent \ "respuesta").get.toString mustBe "109400001"
      }

    }

    "Eliminar paciente" when {
      "Se invoca el servicio" in {
        val request = FakeRequest(DELETE, "/patient/109400001")

        val response = route(app, request).get

        status(response) mustBe (OK)
        val jsonContent = contentAsJson(response)
        (jsonContent \ "respuesta").get.toString mustBe "109400001"
      }
    }
  }
}
