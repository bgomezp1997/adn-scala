package com.ceiba.config.driver.template

import com.ceiba.domain.Result.Response

trait ConsultParameterDriver[P, R] {
  def execute(comando: P): Response[R]
}
