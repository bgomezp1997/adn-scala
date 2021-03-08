package com.ceiba.application.command.patient

import com.ceiba.config.driver.Driver._
import com.ceiba.domain.persistence.repository.PatientRepository
import com.ceiba.domain.model.entity.Patient.IdPatient
import com.ceiba.domain.service.PatientService
import com.ceiba.config.driver.CommandResponse
import com.ceiba.config.driver.template.CommandResponseDriver

import javax.inject.Inject

class DeletePatientDriver @Inject()(patientRepository: PatientRepository, patientService: PatientService) extends CommandResponseDriver[IdPatient, IdPatient] {

  override def execute(idPatient: IdPatient): ResponseCommand[Long] = {
    patientService.delete(idPatient)
      .run(patientRepository)
      .map(response => CommandResponse(response))
  }

}