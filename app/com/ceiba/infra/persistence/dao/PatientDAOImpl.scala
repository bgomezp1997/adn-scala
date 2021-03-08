package com.ceiba.infra.persistence.dao

import cats.data.EitherT
import cats.implicits._
import com.ceiba.config.persistence.SlickDao
import com.ceiba.domain.Result.Response
import com.ceiba.domain.persistence.dao.PatientDAO
import com.ceiba.domain.validation.ErrorModel
import com.ceiba.domain.model.dto.PatientDTO
import com.ceiba.domain.persistence.table.Tables

import javax.inject.Inject
import monix.eval.Task
import play.api.db.slick.DatabaseConfigProvider

class PatientDAOImpl @Inject() (val dbConfigProvider: DatabaseConfigProvider) extends PatientDAO with SlickDao with Tables {

  import com.ceiba.infra.util.transformation.TransformationFuture._
  import com.ceiba.infra.persistence.mapper.PatientDTOMapper._

  import profile.api._

  override def list: Task[List[PatientDTO]] = {
    val query = patients.result
    db.run(query).deferFuture.map(toGenerationMap)
  }

  override def findById(id: Long): Response[PatientDTO] = EitherT {
    val query = patients.filter(patient => patient.identification === id).result
    db.run(query).deferFuture.map(x => x.headOption match {
      case None => ErrorModel.notExist().asLeft
      case Some(patient) => fromResultToPatientDTO(patient).asRight
    })
  }

}
