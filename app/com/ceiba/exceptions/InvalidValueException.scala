package com.ceiba.exceptions

case class InvalidValueException(value: String = "Valor invalido", throwable: Throwable) extends Exception(value, throwable)
