package com.ceiba.infra.persistence.repository

import cats.data.EitherT
import cats.implicits._
import com.ceiba.config.persistence.SlickRepository
import com.ceiba.domain.Result.Response
import com.ceiba.domain.persistence.repository.PatientRepository
import com.ceiba.domain.validation.ErrorModel
import com.ceiba.domain.model.entity.Patient
import com.ceiba.domain.model.entity.Patient.IdPatient
import com.ceiba.domain.persistence.table.Tables
import monix.eval.Task
import play.api.db.slick.DatabaseConfigProvider
import javax.inject.Inject

class PatientRepositoryImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends PatientRepository with SlickRepository with Tables {

  import com.ceiba.infra.util.transformation.TransformationFuture._
  import profile.api._

  override def insert(patient: Patient): Response[IdPatient] = EitherT {
    val information = obtainInformation(patient)
    val query = patients += (information)
    db.run(query.transactionally).deferFuture.map(_ => patient.identification.asRight[ErrorModel])
  }

  override def upgrade(patient: Patient): Response[Long] = EitherT {
    val information = obtainInformation(patient)
    val query = patients.filter(b => b.identification === patient.identification).update(information)
    db.run(query.transactionally).deferFuture.map(_ => patient.identification.asRight[ErrorModel])
  }

  override def delete(id: IdPatient): Response[Long] = EitherT {
    db.run(patients.filter(_.identification === id).delete).deferFuture.map(_ => id.asRight[ErrorModel])
  }

  override def exist(id: IdPatient): Task[Boolean] = {
    val s = patients.filter(b => b.identification === id).exists
    db.run(s.result).deferFuture
  }

  private def obtainInformation(patient: Patient) = {
    (patient.identification, patient.name, patient.lastName, Some(java.sql.Date.valueOf(patient.creationDate.toLocalDate)), patient.email, patient.nitEps, patient.stratum)
  }
}