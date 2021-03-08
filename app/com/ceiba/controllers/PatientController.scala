package com.ceiba.controllers

import com.ceiba.controllers.formatter.FormatterController
import com.ceiba.driver.command.PatientCommand
import com.ceiba.driver.command.patient.{CreatePatientDriver, DeletePatientDriver, UpgradePatientDriver}
import com.ceiba.driver.consult.{ConsultPatientDriver, ListPatientDriver}
import com.ceiba.util.ActionController._
import com.ceiba.model.dto.PatientDTO
import com.ceiba.service.PatientService
import io.swagger.annotations.{Api, ApiOperation}
import play.api.libs.json.Json
import play.api.mvc._

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Api(value = "/patient", tags = Array("Controlador de pacientes"))
class PatientController @Inject()(cc: ControllerComponents,
                                  createPatientDriver: CreatePatientDriver,
                                  deletePatientDriver: DeletePatientDriver,
                                  upgradePatientDriver: UpgradePatientDriver,
                                  listPatientDriver: ListPatientDriver,
                                  consultPatientDriver: ConsultPatientDriver) extends AbstractController(cc) with FormatterController {

  @ApiOperation("Crear paciente")
  def create() = Action.async(parse.json[PatientCommand]) { implicit request: Request[PatientCommand] =>
    createPatientDriver.execute(request.body)
  }

  @ApiOperation("Eliminar paciente")
  def delete(id: Long) = Action.async { implicit request: Request[AnyContent] =>
    deletePatientDriver.execute(id)
  }

  @ApiOperation("Actualizar paciente")
  def upgrade() = Action.async(parse.json[PatientCommand]) { implicit request: Request[PatientCommand] =>
    upgradePatientDriver.execute(request.body)
  }

  @ApiOperation("Listar todos los pacientes")
  def list() = Action.async { implicit request: Request[AnyContent] =>
    listPatientDriver.execute()
  }

  @ApiOperation("Bucar paciente por identificacion")
  def consult(id: Long) = Action.async { implicit request: Request[AnyContent] =>
    consultPatientDriver.execute(id)
  }

}
