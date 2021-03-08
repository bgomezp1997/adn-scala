package com.ceiba.application.command.patient

import cats.data.EitherT
import com.ceiba.domain.persistence.repository.PatientRepository
import com.ceiba.application.command.PatientCommand
import com.ceiba.application.command.fabric.PatientFabric
import com.ceiba.domain.model.entity.Patient.IdPatient
import com.ceiba.domain.service.PatientService
import com.ceiba.config.driver.Driver.ResponseCommand
import com.ceiba.config.driver.CommandResponse
import com.ceiba.config.driver.template.CommandResponseDriver

import javax.inject.Inject

class UpgradePatientDriver @Inject() (patientRepository: PatientRepository, patientService: PatientService) extends CommandResponseDriver[PatientCommand, IdPatient] {

  override def execute(patientCommand: PatientCommand): ResponseCommand[IdPatient] = {
    for {
      patient <- EitherT(PatientFabric.create(patientCommand))
      response <- patientService.upgrade(patient).run(patientRepository)
    } yield CommandResponse(response)
  }

}
