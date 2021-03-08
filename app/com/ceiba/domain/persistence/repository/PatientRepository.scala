package com.ceiba.domain.persistence.repository

import com.ceiba.config.persistence.BaseRepository
import com.ceiba.model.entity.Patient
import com.ceiba.model.entity.Patient.IdPatient
import com.ceiba.domain.Result.Response
import monix.eval.Task

trait PatientRepository extends BaseRepository[Patient] {

  def insert(patient: Patient): Response[IdPatient]

  def upgrade(patient: Patient): Response[Long]

  def delete(id: IdPatient): Response[Long]

  def exist(id: IdPatient): Task[Boolean]

}
