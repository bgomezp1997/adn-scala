package com.ceiba.error

import com.ceiba.error.Error._
import com.ceiba.exceptions.TechnicalException

import javax.inject._
import play.api._
import play.api.http.DefaultHttpErrorHandler
import play.api.mvc.Results._
import play.api.mvc._
import play.api.routing.Router

import scala.concurrent._

@Singleton
class HandleError @Inject() (env: Environment, config: Configuration, sourceMapper: OptionalSourceMapper, router: Provider[Router]) extends DefaultHttpErrorHandler(env, config, sourceMapper, router) {

  val logger: Logger = Logger(this.getClass())

  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    val message = s"Internal server error, for (${request.method}) [${request.uri}]"
    logger.error(message, exception)
    val result = exception match {
      case e: TechnicalException => InternalServerError(generateErrorMessage(s"Error Servidor: ${e.getMessage}", Technical))
      case e: Throwable => InternalServerError(generateErrorMessage(s"Peticion invalida: ${e.getMessage}", Technical))
    }

    Future.successful(result)
  }

  override protected def onBadRequest(request: RequestHeader, message: String): Future[Result] = {
    logger.error(message)
    if (message.contains("Invalid Json:")) {
      Future.successful(
        BadRequest(generateErrorMessage("La peticion no contiene un json valido", Business)))
    } else if (message.contains("Json validation error")) {
      Future.successful(
        BadRequest(generateErrorMessage("Campo invalido", Business)))
    } else {
      super.onBadRequest(request, message)
    }

  }

}
