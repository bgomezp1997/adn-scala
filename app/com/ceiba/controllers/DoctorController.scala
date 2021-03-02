package com.ceiba.controllers

import actions.AuthAction
import com.ceiba.models.dtos.{DateDTO, DoctorDTO}
import com.ceiba.services.{DateService, DoctorService}
import play.api.libs.json.Json
import play.api.mvc._

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class DoctorController @Inject()(cc: ControllerComponents, doctorService: DoctorService) extends AbstractController(cc) {

  def list() = Action.async {
    implicit request: Request[AnyContent] =>
      doctorService.getAllDoctors().map(s => Ok(Json.toJson(s)))
  }

  def add() = Action.async(parse.json[DoctorDTO]) { request =>
    insertDoctor(request.body)
  }

  private def insertDoctor(doctorDTO: DoctorDTO): Future[Result] = {
    doctorService.save(doctorDTO)
      .map(_ => Ok("The doctor was successfully saved"))
      .recoverWith {
        case _: Exception => Future.successful(InternalServerError("The record could not be saved"))
      }
  }

}
