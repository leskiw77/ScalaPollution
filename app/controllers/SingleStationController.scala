package controllers

import play.api.mvc._
import play.api.mvc.Action
import service.station.SingleStationInfoService

object SingleStationController extends Controller {

  def getStationInfo(id: Int) = Action {
    try {
      Ok(SingleStationInfoService.getStationMeasurementJson(id))
    } catch {
      case ioe: java.io.IOException => InternalServerError(ioe.toString)
      case ste: java.net.SocketTimeoutException => InternalServerError(ste.toString)
    }
  }

}
