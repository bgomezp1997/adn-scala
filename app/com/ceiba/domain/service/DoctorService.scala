package com.ceiba.domain.service

import com.ceiba.domain.model.dto.DoctorDTO
import com.ceiba.domain.model.entity.Doctor
import com.ceiba.infra.persistence.repository.DoctorRepository
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
