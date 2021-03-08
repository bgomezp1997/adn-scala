package com.ceiba.config.service

import cats.data.{EitherT, NonEmptyList, Reader}
import com.ceiba.config.persistence.BaseRepository
import com.ceiba.domain.validation.ErrorModel
import monix.eval.Task

import scala.concurrent.Future
import scala.language.higherKinds

object BaseService {

  type RepoReaderFuture[D] = Reader[BaseRepository[_], Future[D]]

  type RepoReaderT[D, E <: ErrorModel] = Reader[BaseRepository[_], EitherT[Task, E, D]]

  def readerFuture[D](f: BaseRepository[_] => Future[D]): RepoReaderFuture[D] =
    Reader[BaseRepository[_], Future[D]](f)

  def reader[D, E <: ErrorModel](f: BaseRepository[_] => EitherT[Task, E, D]): RepoReaderT[D, E] =
    Reader[BaseRepository[_], EitherT[Task, E, D]](f)

  def toEmptyList(error: ErrorModel): NonEmptyList[ErrorModel] = NonEmptyList.one(error)

}
