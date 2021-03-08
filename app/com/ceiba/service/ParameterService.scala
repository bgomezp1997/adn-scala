package com.ceiba.service

import com.ceiba.model.dto.ParameterDTO
import com.ceiba.model.entity.Parameter
import com.ceiba.persistence.repository.ParameterRepository
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
