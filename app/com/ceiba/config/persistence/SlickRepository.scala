package com.ceiba.config.persistence

import cats.data.Reader
import play.api.db.slick.HasDatabaseConfigProvider
import slick.dbio.Effect.Write
import slick.jdbc.JdbcProfile

trait SlickRepository extends HasDatabaseConfigProvider[JdbcProfile] with SlickQueryOps {

  import profile.api._

  type DBIOActionReader[T] = Reader[T, Option[DBIOAction[Any, NoStream, Write]]]

  def DbioActionReader[T](f: T => Option[DBIOAction[Any, NoStream, Write]]): DBIOActionReader[T] =
    Reader[T, Option[DBIOAction[Any, NoStream, Write]]](f)

}
