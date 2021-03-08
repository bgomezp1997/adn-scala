package com.ceiba.driver.command.fabric

import com.ceiba.domain.validation.ErrorModel
import com.ceiba.driver.command.PatientCommand
import com.ceiba.model.entity.Patient
import monix.eval.Task

object PatientFabric {

  def create(patientCommand: PatientCommand): Task[Either[ErrorModel, Patient]] = {
    Task.now(Patient.validateCreatePatient(patientCommand.identification, patientCommand.name, patientCommand.lastName, patientCommand.creationDate, patientCommand.email, patientCommand.nitEps, patientCommand.stratum))
  }

}
