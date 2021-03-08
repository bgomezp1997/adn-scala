package com.ceiba.error

import java.text.SimpleDateFormat
import java.util.Calendar

import play.api.libs.json.Json

object Error {

  def generateErrorMessage(message: String, errorType: ErrorType) = {
    Json.obj("codigo" -> getCode, "tipo" -> errorType.toString, "mensaje" -> message)
  }

  private def getCode: String = {
    s"ERR-$getDate"
  }

  private def getDate: String = {
    val now = Calendar.getInstance().getTime()
    val format = new SimpleDateFormat("yyMMddHHmmssSSS")
    format.format(now)
  }

}

