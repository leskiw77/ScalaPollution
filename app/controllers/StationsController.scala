package controllers

import play.api.mvc._
import play.api.mvc.Action
import service.{GetAllService, StationsForAirQualityService}

object StationsController extends Controller {

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
      Ok(StationsForAirQualityService.getStationsIdJsonForAirQuality(airQualityIndex))
    } catch {
      case ioe: java.io.IOException => InternalServerError(ioe.toString)
      case ste: java.net.SocketTimeoutException => InternalServerError(ste.toString)
    }
  }

}
