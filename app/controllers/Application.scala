package controllers

import play.api.mvc._
import service.station.GetAllService

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Hello", GetAllService.getAll))
  }

}
