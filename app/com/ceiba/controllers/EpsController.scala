package com.ceiba.controllers

import com.ceiba.model.dto.{DateDTO, EpsDTO}
import com.ceiba.service.{DateService, EpsService}
import play.api.libs.json.Json
import play.api.mvc._

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class EpsController @Inject()(cc: ControllerComponents, epsService: EpsService) extends AbstractController(cc) {

  def list() = Action.async {
    implicit request: Request[AnyContent] =>
      epsService.getAllEpss().map(s => Ok(Json.toJson(s)))
  }

  def add() = Action.async(parse.json[EpsDTO]) { request =>
    insertEps(request.body)
  }

  private def insertEps(epsDTO: EpsDTO): Future[Result] = {
    epsService.save(epsDTO)
      .map(_ => Ok("The eps was successfully saved"))
      .recoverWith {
        case _: Exception => Future.successful(InternalServerError("The record could not be saved"))
      }
  }

}
