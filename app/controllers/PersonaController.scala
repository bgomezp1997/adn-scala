package controllers

import actions.AuthAction
import model.Persona
import play.api.libs.functional.syntax.{toApplicativeOps, toFunctionalBuilderOps, unlift}
import play.api.libs.json.Reads.minLength
import play.api.libs.json.{JsError, JsPath, Json, Reads, Writes}
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request, Result}
import repositories.PersonaRepository

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class PersonaController @Inject()(authAction: AuthAction, cc: ControllerComponents, personaRepository: PersonaRepository) extends AbstractController(cc) {

  def validateJson[A: Reads] = parse.json.validate(
    _.validate[A].asEither.left.map(e => BadRequest(JsError.toJson(e)))
  )

  def savePersonaConciseInMemory = Action(validateJson[Persona]) { request =>
    // `request.body` contains a fully validated `Persona` instance.
    val persona = request.body
    Persona.save(persona)
    Ok(Json.obj("status" -> "OK", "message" -> (s"Persona with name '${persona.name}' saved.")))
  }

  def listPersonasInMemory = authAction {
    val json = Json.toJson(Persona.list)
    Ok(json)
  }

  def listPersonasInDB() = Action.async {
    implicit request: Request[AnyContent] =>
      val fCoffees: Future[Seq[Persona]] = personaRepository.all()
      fCoffees.map(s => Ok(Json.toJson(s)))
  }

  def lowerAgeInDB(limit: Int) = Action.async {
    implicit request: Request[AnyContent] =>
      val fCoffees: Future[Seq[Persona]] = personaRepository.lowerAge(limit)
      fCoffees.map(s => Ok(Json.toJson(s)))
  }

  def addInDB() = Action.async(parse.json[Persona]) { request =>
    insertPersona(request.body)
  }

  private def insertPersona(persona: Persona): Future[Result] = {
    personaRepository.insert(persona)
      .map(_ => Ok(""))
      .recoverWith {
        case _: Exception => Future.successful(InternalServerError("No pudo guardarse el registro"))
      }
  }

  private def populateDate() {
    Persona.list.foreach(p => insertPersona(p))
  }

}
