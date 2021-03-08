package com.ceiba.config.driver.template

trait CommandDriver[P] {
  def execute(command: P): Unit
}
