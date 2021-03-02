package com.ceiba.services

import com.ceiba.models.dtos.{EpsDTO, ParameterDTO}
import com.ceiba.models.entities.{Eps, Parameter}
import com.ceiba.repositories.{EpsRepository, ParameterRepository}
import play.api.Logger

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ParameterService @Inject()(parameterRepository: ParameterRepository)(implicit executionContext: ExecutionContext) {

  private val logger: Logger = Logger(this.getClass)

  def getAllParamters(): Future[Seq[Parameter]] = {
    parameterRepository.all()
  }

  def save(parameterDTO: ParameterDTO): Future[Unit] = {
    val parameter: Parameter = parameterDTO
    parameterRepository.insert(parameter)
  }

}
