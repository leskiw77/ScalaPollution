package controllers

import javax.inject.Inject

import play.api.mvc.{Action, _}
import service.station.SingleStationInfoService


class SingleStationController @Inject()(stationService: SingleStationInfoService) extends Controller {

  def getStationInfo(id: Int) = Action {
    try {
      Ok(stationService.getStationMeasurementJson(id))
    } catch {
      case ioe: java.io.IOException => InternalServerError(ioe.toString)
      case ste: java.net.SocketTimeoutException => InternalServerError(ste.toString)
    }
  }

}
