package com.ceiba.controllers

import actions.AuthAction
import com.ceiba.models.dtos.PatientDTO
import com.ceiba.services.PatientService
import play.api.libs.json.Json
import play.api.mvc._

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class PatientController @Inject()(authAction: AuthAction, cc: ControllerComponents, patientService: PatientService) extends AbstractController(cc) {

  def list() = Action.async {
    implicit request: Request[AnyContent] =>
      patientService.getAllPatients().map(s => Ok(Json.toJson(s)))
  }

  def lowerStratum(stratum: Int) = Action.async {
    implicit request: Request[AnyContent] =>
      patientService.getByStratum(stratum).map(s => Ok(Json.toJson(s)))
  }

  def add() = Action.async(parse.json[PatientDTO]) { request =>
    insertPatient(request.body)
  }

  private def insertPatient(patientDTO: PatientDTO): Future[Result] = {
    patientService.save(patientDTO)
      .map(_ => Ok("The patient was successfully saved"))
      .recoverWith {
        case _: Exception => Future.successful(InternalServerError("The record could not be saved"))
      }
  }

}
