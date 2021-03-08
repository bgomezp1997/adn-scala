package com.ceiba.infra.persistence.repository

import com.ceiba.domain.model.entity.Parameter
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ParameterRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val Parameters = TableQuery[ParameterTable]

  def all(): Future[Seq[Parameter]] = db.run(Parameters.result)

  def insert(parameter: Parameter): Future[Unit] = db.run(Parameters += parameter).map { _ => () }

  private class ParameterTable(tag: Tag) extends Table[Parameter](tag, "parameter") {

    def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def value = column[String]("value")
    def types = column[String]("types")

    def * = (id, name, value, types) <> ((Parameter.apply _).tupled, Parameter.unapply)
  }

}