package com.ceiba.controllers

import com.ceiba.model.dto.{EpsDTO, ParameterDTO}
import com.ceiba.service.{EpsService, ParameterService}
import play.api.libs.json.Json
import play.api.mvc._

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ParameterController @Inject()(cc: ControllerComponents, parameterService: ParameterService) extends AbstractController(cc) {

  def list() = Action.async {
    implicit request: Request[AnyContent] =>
      parameterService.getAllParameters().map(s => Ok(Json.toJson(s)))
  }

  def add() = Action.async(parse.json[ParameterDTO]) { request =>
    insertEps(request.body)
  }

  private def insertEps(parameterDTO: ParameterDTO): Future[Result] = {
    parameterService.save(parameterDTO)
      .map(_ => Ok("The parameter was successfully saved"))
      .recoverWith {
        case _: Exception => Future.successful(InternalServerError("The record could not be saved"))
      }
  }

}
