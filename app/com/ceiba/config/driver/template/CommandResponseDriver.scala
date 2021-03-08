package com.ceiba.config.driver.template

import com.ceiba.config.driver.Driver.ResponseCommand

trait CommandResponseDriver[P, R] {
  def execute(command: P): ResponseCommand[R]
}
