package com.ceiba.services

import com.ceiba.models.dtos.{DoctorDTO, EpsDTO}
import com.ceiba.models.entities.{Doctor, Eps}
import com.ceiba.repositories.{DoctorRepository, EpsRepository}
import play.api.Logger

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class EpsService @Inject()(epsRepository: EpsRepository)(implicit executionContext: ExecutionContext) {

  private val logger: Logger = Logger(this.getClass)

  def getAllEpss(): Future[Seq[Eps]] = {
    epsRepository.all()
  }

  def save(epsDTO: EpsDTO): Future[Unit] = {
    val eps: Eps = epsDTO
    epsRepository.insert(eps)
  }

}
