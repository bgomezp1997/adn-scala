package com.ceiba.domain

import cats.data.EitherT
import com.ceiba.domain.validation.ErrorModel
import monix.eval.Task

object Result {

  type Response[T] = EitherT[Task, ErrorModel, T]

}
