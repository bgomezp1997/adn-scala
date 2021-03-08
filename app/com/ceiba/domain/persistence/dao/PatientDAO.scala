package com.ceiba.domain.persistence.dao

import com.ceiba.model.dto.PatientDTO
import com.ceiba.model.entity.Patient.IdPatient
import com.ceiba.domain.Result.Response
import monix.eval.Task

trait PatientDAO {

  def list: Task[List[PatientDTO]]

  def findById(id: IdPatient): Response[PatientDTO]

}
