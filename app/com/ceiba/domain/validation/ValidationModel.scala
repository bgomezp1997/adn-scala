package com.ceiba.domain.validation

import cats.data._
import cats.implicits._
import com.ceiba.exceptions.InvalidValueException

import java.time.LocalDateTime
import scala.util.{Failure, Success, Try}

object ValidationModel {

  type ValidationResult[A] = ValidatedNel[DetailErrorModel, A]

  implicit def toEitherDominio[T](e: ValidationResult[T]): Either[ErrorModel, T] = {
    e.toEither match {
      case Left(value) => Left(ErrorsModel(value))
      case Right(patient) => Right(patient)
    }
  }

  def validateMandatory[T](valor: Option[T], message: String): ValidationResult[T] = {
    Either.cond(
      valor.isDefined,
      valor.get,
      ErrorModel.mandatoryValue(message)).toValidatedNel
  }

  def validateMandatoryLength(valor: Option[String], longitud: Int, message: String): ValidationResult[Option[String]] = {
    valor match {
      case None => ErrorModel.mandatoryValue().invalidNel
      case Some(valorOpt) if (valorOpt.length <= longitud) => valor.validNel
      case Some(valorOpt) if (valorOpt.length > longitud) => ErrorModel.invalidLength(message).invalidNel
    }
  }

  def validateLength(valor: String, longitud: Int, message: String): ValidationResult[String] = {
    Either.cond(valor.length < longitud, valor, ErrorModel.invalidLength(message)).toValidatedNel
  }

  def validateNotEmpty[T](lista: List[T], message: String): ValidationResult[List[T]] = {
    Either.cond(!lista.isEmpty, lista, ErrorModel.mandatoryValue(message)).toValidatedNel
  }

  def validatePositive(valor: Double, message: String): ValidationResult[Double] = {
    Either.cond(valor > 0, valor, ErrorModel.invalidValue(message)).toValidatedNel
  }

  def validateSame(valor: Double, valorEsperado: Double, message: String): ValidationResult[Double] = {
    Either.cond(valor == valorEsperado, valor, ErrorModel.invalidValue(message)).toValidatedNel
  }

  def validateMaxLength(valor: Any, longitudMinima: Int, message: String): ValidationResult[Any] = {
    Either.cond(valor.toString.length > longitudMinima, valor, ErrorModel.invalidLength(message)).toValidatedNel
  }

  def validateMinor(fechaInicial: LocalDateTime, fechaFinal: LocalDateTime, message: String): ValidationResult[LocalDateTime] = {
    Either.cond(fechaInicial.toLocalDate.isBefore(fechaFinal.toLocalDate), fechaInicial, ErrorModel.invalidValue(message)).toValidatedNel
  }

  def validateMinor(numeroInicial: Long, numeroFinal: Long, message: String): ValidationResult[Long] = {
    Either.cond(numeroInicial > numeroFinal, numeroInicial, ErrorModel.invalidValue(message)).toValidatedNel
  }

  def validateRegex(valor: String, regex: String, message: String): ValidationResult[String] = {
    Either.cond(valor.matches(regex), valor, ErrorModel.invalidValue(message)).toValidatedNel
  }

  def validateValid[E <: Enum[E]](valor: String, enumAObtener: Class[E], message: String): ValidationResult[E] = {
    val resultadoOpcional = enumAObtener.getEnumConstants.filter((resultado: E) => resultado.toString == valor).headOption
    Either.cond(resultadoOpcional.isDefined, resultadoOpcional.get, ErrorModel.invalidValue(message)).toValidatedNel

  }

  def validateNumeric(valor: String, message: String): ValidationResult[Long] = {
    val respuesta = Try(valor.toLong) match {
      case Success(value) => Right(value)
      case Failure(exception) => Left(ErrorModel.invalidValue(message))
    }
    respuesta.toValidatedNel
  }

  def toValidatedNel[T](valor: T): ValidationResult[T] = {
    valor.validNel[DetailErrorModel]
  }

  implicit def optToValue[T](valueOpT: Option[T]): T = {
    try
      valueOpT.get
    catch {
      case exception: Exception =>
        throw InvalidValueException("Valor invalido", exception)
    }
  }

}
