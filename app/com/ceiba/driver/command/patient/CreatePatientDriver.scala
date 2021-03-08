package com.ceiba.driver.command.patient

import cats.data.EitherT
import com.ceiba.domain.persistence.repository.PatientRepository
import com.ceiba.driver.command.PatientCommand
import com.ceiba.driver.command.fabric.PatientFabric
import com.ceiba.model.entity.Patient.IdPatient
import com.ceiba.service.PatientService
import com.ceiba.config.driver.Driver._
import com.ceiba.config.driver.CommandResponse
import com.ceiba.config.driver.template.CommandResponseDriver

import javax.inject.Inject

class CreatePatientDriver @Inject() (patientRepository: PatientRepository, patientService: PatientService) extends CommandResponseDriver[PatientCommand, IdPatient] {

  override def execute(patientCommand: PatientCommand): ResponseCommand[IdPatient] = {
    for {
      patient <- EitherT(PatientFabric.create(patientCommand))
      response <- patientService.create(patient).run(patientRepository)
    } yield CommandResponse(response)
  }

}
