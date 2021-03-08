package com.ceiba.domain.validation

import cats.data.NonEmptyList

sealed trait ErrorModel

final case class ErrorsModel(errores: NonEmptyList[DetailErrorModel]) extends ErrorModel

sealed trait DetailErrorModel extends ErrorModel {
  val message: String
}

object ErrorModel {

  final case class DuplicateItem(message: String) extends DetailErrorModel
  final case class ItemNotExist(message: String) extends DetailErrorModel
  final case class InvalidLength(message: String) extends DetailErrorModel
  final case class InvalidValue(message: String) extends DetailErrorModel
  final case class MandatoryValue(message: String) extends DetailErrorModel

  def notExist(message: String = "Elemento no existe"): DetailErrorModel = ItemNotExist(message)
  def exist(message: String = "Elemento duplicado"): DetailErrorModel = DuplicateItem(message)
  def invalidLength(message: String = "Longitud invalida"): DetailErrorModel = InvalidLength(message)
  def invalidValue(message: String = "Valor invalido"): DetailErrorModel = InvalidValue(message)
  def mandatoryValue(message: String = "Valor obligatorio"): DetailErrorModel = MandatoryValue(message)

}
