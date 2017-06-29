package controllers

import play.api.mvc._
import play.api.mvc.Action
import service.SingleStationInfoService

object SingleStationController extends Controller {

  def getStationInfo(id: Int) = Action {
    try {
      Ok(SingleStationInfoService.getMeasurementsForStation(id))
    } catch {
      case ioe: java.io.IOException => InternalServerError(ioe.toString)
      case ste: java.net.SocketTimeoutException => InternalServerError(ste.toString)
    }
  }

}
