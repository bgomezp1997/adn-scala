package com.ceiba.infra.util.transformation

import monix.eval.Task
import monix.execution.Scheduler

import scala.concurrent.Future

object TransformationFuture {

  implicit class FutureExtension[T](val future: Future[T]) extends AnyVal {
    def deferFuture(): Task[T] = {
      Task.deferFutureAction { implicit scheduler: Scheduler => future }
    }
  }

}
