package controllers

import play.api.mvc._
import service.GetAllService

object GetAllController extends Controller  {

  def getAll = Action{
    try {
      Ok(GetAllService.getAll().mkString)
    } catch {
      case ioe: java.io.IOException =>  InternalServerError(ioe.toString)
      case ste: java.net.SocketTimeoutException => InternalServerError(ste.toString)
    }
  }
}
