package com.ceiba.controllers

import com.ceiba.model.dto.{DateDTO, PatientDTO}
import com.ceiba.service.{DateService, PatientService}
import play.api.libs.json.Json
import play.api.mvc._

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class DateController @Inject()(cc: ControllerComponents, dateService: DateService) extends AbstractController(cc) {

  def list() = Action.async {
    implicit request: Request[AnyContent] =>
      dateService.getAllDate().map(s => Ok(Json.toJson(s)))
  }

  def add() = Action.async(parse.json[DateDTO]) { request =>
    insertDate(request.body)
  }

  private def insertDate(dateDTO: DateDTO): Future[Result] = {
    dateService.save(dateDTO)
      .map(_ => Ok("The date was successfully saved"))
      .recoverWith {
        case _: Exception => Future.successful(InternalServerError("The record could not be saved"))
      }
  }

}
