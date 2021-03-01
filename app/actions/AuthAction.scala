package actions

import play.Logger
import play.api.mvc.Results.Forbidden
import play.api.mvc.{Action, ActionBuilderImpl, BodyParsers, Request, Result}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class AuthAction @Inject()(parser: BodyParsers.Default)(implicit ec: ExecutionContext) extends ActionBuilderImpl(parser) {

  override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
    request.headers
      .get("tkn")
      .collect {
        case _ => block(request)
      }
      .getOrElse {
        Future.successful(Forbidden("The request doesn't have the expected header"))
      }
  }

}
