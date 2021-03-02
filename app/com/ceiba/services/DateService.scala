package com.ceiba.services

import com.ceiba.models.dtos.DateDTO
import com.ceiba.models.entities.Date
import com.ceiba.repositories.DateRepository
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
