package com.ceiba.services

import com.ceiba.models.dtos.DoctorDTO
import com.ceiba.models.entities.Doctor
import com.ceiba.repositories.DoctorRepository
import play.api.Logger

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class DoctorService @Inject()(doctorRepository: DoctorRepository)(implicit executionContext: ExecutionContext) {

  private val logger: Logger = Logger(this.getClass)

  def getAllDoctors(): Future[Seq[Doctor]] = {
    doctorRepository.all()
  }

  def save(doctorDTO: DoctorDTO): Future[Unit] = {
    val doctor: Doctor = doctorDTO
    doctorRepository.insert(doctor)
  }

}
