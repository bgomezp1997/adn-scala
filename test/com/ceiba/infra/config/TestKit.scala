package com.ceiba.infra.config

import com.ceiba.domain.persistence.dao.PatientDAO
import com.ceiba.domain.persistence.repository.PatientRepository
import com.ceiba.domain.persistence.table.Tables
import com.ceiba.infra.persistence.dao.PatientDAOImpl
import com.ceiba.infra.persistence.repository.PatientRepositoryImpl
import com.typesafe.config.ConfigFactory
import org.scalatest.BeforeAndAfterAll
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api._
import play.api.db.{DBApi, Database}
import play.api.db.evolutions.{DatabaseEvolutions, EvolutionsReader, ThisClassLoaderEvolutionsReader}
import play.api.db.evolutions.Evolutions
import play.api.http.Port
import play.api.inject.DefaultApplicationLifecycle
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.mvc._
import play.api.routing.Router
import play.core.server.{Server, ServerConfig, ServerProvider}
import slick.basic.DatabaseConfig
import slick.jdbc.H2Profile
import play.api.inject.bind

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext}

abstract class TestKit extends PlaySpec {
  implicit val ec = ExecutionContext.global
}

abstract class AppTestKit extends TestKit with GuiceOneAppPerSuite {

  final protected val appBuilder = new GuiceApplicationBuilder()

  lazy val injector = appBuilder.injector()

  lazy val databaseApi = injector.instanceOf[DBApi]

  final val conf: Configuration = Configuration(ConfigFactory.load("application-test.conf"))

  override def fakeApplication(): Application = appBuilder.configure(conf)
    .overrides(
      bind(classOf[PatientRepository]).to(classOf[PatientRepositoryImpl]),
      bind(classOf[PatientDAO]).to(classOf[PatientDAOImpl])).build()

}

object TestKit {

  def withRouter[T](config: ServerConfig = ServerConfig(port = Some(0), mode = Mode.Test))(routes: PartialFunction[RequestHeader, Handler])(block: Port => T)(implicit provider: ServerProvider): T = {

    val conf = Configuration(ConfigFactory.load("application-test.conf"))

    val context = ApplicationLoader.Context(
      environment = Environment.simple(path = config.rootDir, mode = config.mode),
      initialConfiguration = conf,
      lifecycle = new DefaultApplicationLifecycle,
      devContext = None)
    val application = new BuiltInComponentsFromContext(context) with NoHttpFiltersComponents {
      def router = Router.from(routes)
    }.application

    Server.withApplication(application, config)(block)
  }
}
