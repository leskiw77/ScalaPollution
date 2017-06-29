package controllers

import controllers.GetAllController.{InternalServerError, Ok}
import play.api.mvc.Action
import service.{GetAllStationsForAirQualityService}

object GetStationsForAirQuality {
  def getStationsForAirQuality(id: Int) = Action{
    try {
      Ok(GetAllStationsForAirQualityService.getStationsIdJsonForAirQuality(id))
    } catch {
      case ioe: java.io.IOException =>  InternalServerError(ioe.toString)
      case ste: java.net.SocketTimeoutException => InternalServerError(ste.toString)
    }
  }
}
