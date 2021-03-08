package com.ceiba.persistence.repository

import com.ceiba.model.entity.Doctor
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import java.sql.Date
import java.time.LocalDate
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class DoctorRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val Doctors = TableQuery[DoctorTable]

  def all(): Future[Seq[Doctor]] = db.run(Doctors.result)

  def insert(doctor: Doctor): Future[Unit] = db.run(Doctors += doctor).map { _ => () }

  private class DoctorTable(tag: Tag) extends Table[Doctor](tag, "patient") {

    implicit val localDateColumnType = MappedColumnType.base[LocalDate, Date](Date.valueOf, _.toLocalDate)

    def identification = column[Long]("identification", O.PrimaryKey)
    def name = column[String]("name")
    def lastName = column[String]("last_name")
    def creationDate = column[LocalDate]("creation_date")
    def email = column[String]("email")
    def nitEps = column[String]("specialty")
    def stratum = column[Int]("professional_card_number")

    def * = (identification, name, lastName, creationDate, email, nitEps, stratum) <> ((Doctor.apply _).tupled, Doctor.unapply)
  }

}