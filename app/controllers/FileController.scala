package controllers

import play.api.mvc.{AbstractController, ControllerComponents}

import java.nio.file.Paths
import javax.inject.Inject

class FileController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def upload = Action(parse.multipartFormData) { request =>
    request.body
      .file("picture")
      .map { picture =>
        // only get the last part of the filename
        // otherwise someone can send a path like ../../home/foo/bar.txt to write to other files on the system
        val filename = Paths.get(picture.filename).getFileName

        picture.ref.moveTo(Paths.get(s"C:/Users/brian.gomez/Documents/$filename"), replace = true)
        Ok("File uploaded")
      }
      .getOrElse {
        NotFound("Missing file")
      }
  }

}
