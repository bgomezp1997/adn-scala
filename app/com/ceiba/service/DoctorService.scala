package com.ceiba.service

import com.ceiba.model.dto.DoctorDTO
import com.ceiba.model.entity.Doctor
import com.ceiba.persistence.repository.DoctorRepository
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
