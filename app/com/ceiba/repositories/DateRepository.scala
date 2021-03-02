package com.ceiba.repositories

import com.ceiba.models.entities.Date
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import java.time.LocalDate
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class DateRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val Dates = TableQuery[DateTable]

  def all(): Future[Seq[Date]] = db.run(Dates.result)

  def insert(date: Date): Future[Unit] = db.run(Dates += date).map { _ => () }

  private class DateTable(tag: Tag) extends Table[Date](tag, "date") {

    implicit val localDateColumnType = MappedColumnType.base[LocalDate, java.sql.Date](java.sql.Date.valueOf, _.toLocalDate)

    def id = column[Option[Long]]("id", O.PrimaryKey)
    def appointmentDate = column[LocalDate]("appointment_date")
    def identificationPatient = column[Long]("identification_patient")
    def identificationDoctor = column[Long]("identification_doctor")
    def price = column[Double]("price")

    def * = (id, appointmentDate, identificationPatient, identificationDoctor, price) <> ((Date.apply _).tupled, Date.unapply)
  }

}