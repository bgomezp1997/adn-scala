package com.ceiba.config.driver.template

import com.ceiba.domain.Result.Response

trait ConsultDriver[R] {
  def execute(): Response[R]
}
