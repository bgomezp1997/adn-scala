package com.ceiba.driver.consult

import com.ceiba.domain.Result.Response
import com.ceiba.domain.persistence.dao.PatientDAO
import com.ceiba.model.dto.PatientDTO
import com.ceiba.model.entity.Patient.IdPatient
import com.ceiba.config.driver.template.ConsultParameterDriver

import javax.inject.Inject

class ConsultPatientDriver @Inject() (patientDAO: PatientDAO) extends ConsultParameterDriver[IdPatient, PatientDTO] {

  override def execute(idPatient: IdPatient): Response[PatientDTO] = {
    patientDAO.findById(idPatient)
  }

}
