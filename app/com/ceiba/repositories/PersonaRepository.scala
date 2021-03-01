package com.ceiba.repositories

import com.ceiba.models.Persona
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import java.sql.Date
import java.time.LocalDate
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PersonaRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val Personas = TableQuery[PersonaTable]

  def all(): Future[Seq[Persona]] = db.run(Personas.result)

  def insert(persona: Persona): Future[Unit] = db.run(Personas += persona).map { _ => () }

  def lowerAge(limit: Int): Future[Seq[Persona]] = db.run(Personas.filter(_.age < limit.toInt).result)


  private class PersonaTable(tag: Tag) extends Table[Persona](tag, "persona") {

    implicit val localDateColumnType = MappedColumnType.base[LocalDate, Date](Date.valueOf, _.toLocalDate)

    def identification = column[Int]("identification", O.PrimaryKey)
    def name = column[String]("name")
    def lastName = column[String]("last_name")
    def age = column[Int]("age")
    def dateBirth = column[Option[LocalDate]]("date_birth")

    def * = (identification, name, lastName, age, dateBirth) <> ((Persona.apply _).tupled, Persona.unapply)
  }

}