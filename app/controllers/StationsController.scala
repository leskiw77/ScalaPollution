package controllers

import javax.inject.Inject

import play.api.mvc.{Action, _}
import service.station.{GetAllService, StationsForAirQualityService}


class StationsController @Inject()(stationService: StationsForAirQualityService) extends Controller {

  def getAll = Action {
    try {
      Ok(GetAllService.getAll.mkString)
    } catch {
      case ioe: java.io.IOException => InternalServerError(ioe.toString)
      case ste: java.net.SocketTimeoutException => InternalServerError(ste.toString)
    }
  }

  def getStationsByAirQuality(airQualityIndex: Int) = Action {
    try {
      Ok(stationService.getStationsIdJsonForAirQuality(airQualityIndex))
    } catch {
      case ioe: java.io.IOException => InternalServerError(ioe.toString)
      case ste: java.net.SocketTimeoutException => InternalServerError(ste.toString)
    }
  }

}
