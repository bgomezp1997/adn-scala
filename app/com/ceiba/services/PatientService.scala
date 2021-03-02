package com.ceiba.services

import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import com.ceiba.models.dtos.PatientDTO
import com.ceiba.models.entities.Patient
import com.ceiba.repositories.PatientRepository
import play.api.Logger
import play.api.libs.json.Json

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PatientService @Inject() (patientRepository: PatientRepository)(implicit executionContext: ExecutionContext) {

  private val logger: Logger = Logger(this.getClass)

  def getAllPatients(): Future[Seq[Patient]] = {
    patientRepository.all()
  }

  def getByStratum(stratum: Int): Future[Seq[Patient]] = {
    patientRepository.findByStratum(stratum)
  }

  def save(patientDTO: PatientDTO): Future[Unit] = {
    val patient: Patient = patientDTO
    patientRepository.insert(patient)
  }

}
