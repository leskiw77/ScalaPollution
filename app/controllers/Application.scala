package controllers

import play.api.mvc._
import service.GetAllService

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Hello", GetAllService.getAll))
  }

}
