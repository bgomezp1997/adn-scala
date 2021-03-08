package com.ceiba.domain.service

import cats.data.EitherT
import cats.implicits._
import com.ceiba.config.service.BaseService._
import com.ceiba.domain.Result.Response
import com.ceiba.domain.validation.ErrorModel
import com.ceiba.domain.persistence.repository.PatientRepository
import com.ceiba.domain.model.entity.Patient
import com.ceiba.domain.model.entity.Patient.IdPatient

class PatientService {

  def create(patient: Patient): RepoReaderT[IdPatient, ErrorModel] = reader {
    case repo: PatientRepository =>
      for {
        _ <- validateExists(patient.identification)(repo)
        creado <- repo.insert(patient)
      } yield creado
  }

  def upgrade(patient: Patient): RepoReaderT[Long, ErrorModel] = reader {
    case repo: PatientRepository =>
      for {
        _ <- validateNotExists(patient.identification)(repo)
        upgrade <- repo.upgrade(patient)
      } yield upgrade
  }

  def delete(id: Long): RepoReaderT[Long, ErrorModel] = reader {
    case repo: PatientRepository =>
      val r = for {
        _ <- validateNotExists(id)(repo)
        deleted <- repo.delete(id)
      } yield deleted
      r
  }

  private def validateExists(id: IdPatient)(repo: PatientRepository): Response[Boolean] = EitherT {
    repo.exist(id).map(
      existe => existe match {
        case true => ErrorModel.exist().asLeft
        case false => false.asRight
      })
  }

  private def validateNotExists(id: IdPatient)(repo: PatientRepository): Response[Boolean] = EitherT {
    repo.exist(id).map(
      existe => existe match {
        case true => true.asRight
        case false => ErrorModel.notExist().asLeft
      })
  }

}
