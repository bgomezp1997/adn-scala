package com.ceiba.error

trait ErrorType extends Product with Serializable

trait ErrorTypeApplication extends ErrorType

object ErrorTypeApplication {

  def apply(value: String): ErrorTypeApplication = value match {
    case "Technical" => Technical
    case "Business" => Business
    case "Application" => Application
    case _ => Unknown
  }

  def unapply(value: ErrorTypeApplication): String = value match {
    case Technical => "Technical"
    case Business => "Business"
    case Application => "Application"
    case _ => "Unknown"
  }
}

case object Technical extends ErrorTypeApplication

case object Business extends ErrorTypeApplication

case object Application extends ErrorTypeApplication

case object Unknown extends ErrorTypeApplication
