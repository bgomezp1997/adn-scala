package com.ceiba.filters

import akka.stream.Materializer
import play.api.Logger
import play.api.mvc._
import play.api.libs.json.Json
import javax.inject._
import scala.concurrent.{ ExecutionContext, Future }

/**
 * This is a simple filter that adds a header to all requests. It's
 * added to the application's list of filters by the
 * [[Filters]] class.
 *
 * @param mat  This object is needed to handle streaming of requests
 *             and responses.
 * @param exec This class is needed to execute code asynchronously.
 *             It is used below by the `map` method.
 */
@Singleton
class WebFilter @Inject() (implicit override val mat: Materializer, exec: ExecutionContext) extends Filter {

  val logger: Logger = Logger(this.getClass())

  override def apply(nextFilter: RequestHeader => Future[Result])(requestHeader: RequestHeader): Future[Result] = {
    val startTime = System.currentTimeMillis
    nextFilter(requestHeader).map { result =>
      val responseTime: Long = System.currentTimeMillis - startTime
      if (!requestHeader.path.contains("version")
        && !requestHeader.path.contains("assets")
        && !requestHeader.path.contains("swagger")) {
        val record = LogRecord(requestHeader.path, result.header.status, responseTime)
        logger.info(Json.toJson(record).toString())

        result.withHeaders("Ms-Response-Time" -> responseTime.toString)
      } else {
        result
      }
    }
  }

}
