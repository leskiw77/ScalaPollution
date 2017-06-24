package controllers

import play.api.http.Writeable
import play.api.mvc._
import service.GetAllService
/**
  * Created by jarema on 6/25/17.
  */
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
