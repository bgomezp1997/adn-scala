package com.ceiba.controllers

import actions.AuthAction
import com.ceiba.models.dtos.PatientDTO
import com.ceiba.models.entities.Patient
import com.ceiba.services.PatientService
import play.api.libs.json.{JsError, Json, Reads}
import play.api.mvc._

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class PatientController @Inject()(authAction: AuthAction, cc: ControllerComponents, patientService: PatientService) extends AbstractController(cc) {

  def validateJson[A: Reads] = parse.json.validate(
    _.validate[A].asEither.left.map(e => BadRequest(JsError.toJson(e)))
  )

  def list() = Action.async {
    implicit request: Request[AnyContent] =>
      patientService.getAllPatients().map(s => Ok(Json.toJson(s)))
  }

  def lowerStratum(stratum: Int) = Action.async {
    implicit request: Request[AnyContent] =>
      patientService.getByStratum(stratum).map(s => Ok(Json.toJson(s)))
  }

  def add() = Action.async(parse.json[PatientDTO]) { request =>
    insertPersona(request.body)
  }

  private def insertPersona(patientDTO: PatientDTO): Future[Result] = {
    patientService.save(patientDTO)
      .map(_ => Ok("The patient was successfully saved"))
      .recoverWith {
        case _: Exception => Future.successful(InternalServerError("The record could not be saved"))
      }
  }

}
