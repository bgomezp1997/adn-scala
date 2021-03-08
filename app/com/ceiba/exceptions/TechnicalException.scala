package com.ceiba.exceptions

case class TechnicalException(message: String, error: Throwable) extends Exception(message, error)
