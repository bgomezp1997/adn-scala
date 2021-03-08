package com.ceiba.domain.service

import com.ceiba.domain.model.dto.ParameterDTO
import com.ceiba.domain.model.entity.Parameter
import com.ceiba.infra.persistence.repository.ParameterRepository
import play.api.Logger

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ParameterService @Inject()(parameterRepository: ParameterRepository)(implicit executionContext: ExecutionContext) {

  private val logger: Logger = Logger(this.getClass)

  def getAllParameters(): Future[Seq[Parameter]] = {
    parameterRepository.all()
  }

  def save(parameterDTO: ParameterDTO): Future[Unit] = {
    val parameter: Parameter = parameterDTO
    parameterRepository.insert(parameter)
  }

}
