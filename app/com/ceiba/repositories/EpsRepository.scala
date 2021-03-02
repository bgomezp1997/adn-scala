package com.ceiba.repositories

import com.ceiba.models.entities.Eps
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class EpsRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val Epss = TableQuery[EpsTable]

  def all(): Future[Seq[Eps]] = db.run(Epss.result)

  def insert(eps: Eps): Future[Unit] = db.run(Epss += eps).map { _ => () }

  private class EpsTable(tag: Tag) extends Table[Eps](tag, "eps") {

    def nit = column[String]("nit", O.PrimaryKey)
    def name = column[String]("name")
    def phone = column[String]("phone")
    def email = column[String]("email")

    def * = (nit, name, phone, email) <> ((Eps.apply _).tupled, Eps.unapply)
  }

}