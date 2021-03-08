package com.ceiba.application.consult

import com.ceiba.config.driver.template.ConsultParameterDriver
import com.ceiba.domain.Result.Response
import com.ceiba.domain.model.dto.PatientDTO
import com.ceiba.domain.model.entity.Patient.IdPatient
import com.ceiba.domain.persistence.dao.PatientDAO

import javax.inject.Inject

class ConsultPatientDriver @Inject() (patientDAO: PatientDAO) extends ConsultParameterDriver[IdPatient, PatientDTO] {

  override def execute(idPatient: IdPatient): Response[PatientDTO] = {
    patientDAO.findById(idPatient)
  }

}
