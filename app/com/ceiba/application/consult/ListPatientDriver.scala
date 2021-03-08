package com.ceiba.application.consult

import cats.data.EitherT
import cats.implicits._
import com.ceiba.config.driver.template.ConsultDriver
import com.ceiba.domain.Result.Response
import com.ceiba.domain.model.dto.PatientDTO
import com.ceiba.domain.persistence.dao.PatientDAO
import com.ceiba.domain.validation.ErrorModel

import javax.inject.Inject

class ListPatientDriver @Inject() (patientDAO: PatientDAO) extends ConsultDriver[List[PatientDTO]] {

  override def execute(): Response[List[PatientDTO]] = {
    EitherT {
      patientDAO.list.map(s => s.asRight[ErrorModel])
    }
  }
}
