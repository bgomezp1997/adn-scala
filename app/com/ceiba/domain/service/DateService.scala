package com.ceiba.domain.service

import com.ceiba.domain.model.dto.DateDTO
import com.ceiba.domain.model.entity.Date
import com.ceiba.infra.persistence.repository.DateRepository
import play.api.Logger

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class DateService @Inject()(dateRepository: DateRepository)(implicit executionContext: ExecutionContext) {

  private val logger: Logger = Logger(this.getClass)

  def getAllDate(): Future[Seq[Date]] = {
    dateRepository.all()
  }

  def save(dateDTO: DateDTO): Future[Unit] = {
    val date: Date = dateDTO
    dateRepository.insert(date)
  }

}
