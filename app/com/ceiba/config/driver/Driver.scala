package com.ceiba.config.driver

import cats.data.EitherT
import com.ceiba.domain.Result.Response
import com.ceiba.domain.validation.ErrorModel
import monix.eval.Task

object Driver {

  type ResponseCommand[T] = EitherT[Task, ErrorModel, CommandResponse[T]]

  implicit def transform[T](response: Response[T]): ResponseCommand[T] = {
    response.map(respuesta => CommandResponse[T](respuesta))
  }

}
