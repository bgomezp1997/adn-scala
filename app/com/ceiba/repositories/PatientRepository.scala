package com.ceiba.repositories

import com.ceiba.models.dtos.PatientDTO
import com.ceiba.models.entities.Patient
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import java.sql.Date
import java.time.LocalDate
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PatientRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val Patients = TableQuery[PatientTable]

  def all(): Future[Seq[Patient]] = db.run(Patients.result)

  def insert(patient: Patient): Future[Unit] = db.run(Patients += patient).map { _ => () }

  def findByStratum(limit: Int): Future[Seq[Patient]] = db.run(Patients.filter(_.stratum < limit.toInt).result)

  private class PatientTable(tag: Tag) extends Table[Patient](tag, "patient") {

    implicit val localDateColumnType = MappedColumnType.base[LocalDate, Date](Date.valueOf, _.toLocalDate)

    def identification = column[Long]("identification", O.PrimaryKey)
    def name = column[String]("name")
    def lastName = column[String]("last_name")
    def creationDate = column[LocalDate]("creation_date")
    def email = column[String]("email")
    def nitEps = column[String]("nit_eps")
    def stratum = column[Int]("stratum")

    def * = (identification, name, lastName, creationDate, email, nitEps, stratum) <> ((Patient.apply _).tupled, Patient.unapply)
  }

}