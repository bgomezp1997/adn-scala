package com.ceiba.model.table

import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

trait Tables {

  self: HasDatabaseConfigProvider[JdbcProfile] =>

  import profile.api._

  val patients = TableQuery[PatientTable]

  class PatientTable(tag: Tag) extends Table[(Long, String, String, Option[java.sql.Date], String, String, Int)](tag, "patient") {

    def identification = column[Long]("identification", O.PrimaryKey)
    def name = column[String]("name")
    def lastName = column[String]("last_name")
    def creationDate = column[Option[java.sql.Date]]("creation_date")
    def email = column[String]("email")
    def nitEps = column[String]("nit_eps")
    def stratum = column[Int]("stratum")

    def * = (identification, name, lastName, creationDate, email, nitEps, stratum)
  }

}
