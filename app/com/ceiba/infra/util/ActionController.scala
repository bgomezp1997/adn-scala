package com.ceiba.infra.util

import com.ceiba.domain.Result.Response
import com.ceiba.domain.validation.{ErrorModel, ErrorsModel}
import com.ceiba.domain.validation.ErrorModel._
import com.ceiba.config.driver.CommandResponse
import com.ceiba.config.driver.Driver.ResponseCommand
import com.ceiba.error.{Application, Business}
import com.ceiba.error.Error._
import monix.execution.ExecutionModel.AlwaysAsyncExecution
import monix.execution.Scheduler
import play.api.libs.json.{Json, Writes}
import play.api.mvc.Result

import scala.concurrent.Future

object ActionController {

  import play.api.mvc.Results._

  implicit val scheduler = Scheduler.fixedPool(name = "main-pool", poolSize = 10, executionModel = AlwaysAsyncExecution)

  implicit def searchResultsWrites[T](implicit fmt: Writes[T]): Writes[CommandResponse[T]] = new Writes[CommandResponse[T]] {
    def writes(ts: CommandResponse[T]) = Json.obj("respuesta" -> ts.response)
  }

  implicit def transformComando[T](s: ResponseCommand[T])(implicit tjs: play.api.libs.json.Writes[T]): Future[Result] = {
    s.fold(
      { err => clasificarError(err)
      },
      { respuesta => Ok(Json.toJson(respuesta)) }).runToFuture
  }

  implicit def tranformar[T](s: Response[T])(implicit tjs: play.api.libs.json.Writes[T]): Future[Result] = {
    s.fold(
      { err => clasificarError(err) },
      { respuesta => Ok(Json.toJson(respuesta)) }).runToFuture
  }

  private def clasificarError[T](err: ErrorModel) = {
    println(err)
    err match {
      case error: DuplicateItem => BadRequest(generateErrorMessage(error.message, Business))
      case error: ItemNotExist => NotFound(generateErrorMessage(error.message, Business))
      case error: ErrorsModel => BadRequest(generateErrorMessage(error.errores.toList.map(_.message).mkString(" - "), Business))
      case _ => InternalServerError(generateErrorMessage("Error, contacte al administrador", Application))
    }
  }

}
