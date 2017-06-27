package controllers

import controllers.GetAllController.{InternalServerError, Ok}
import play.api.mvc.Action
import service.{GetInformationForLocationService}

/**
  * Created by jarema on 6/27/17.
  */
object GetInfoForCity {
  def getStationInfo(cityName: String) = Action{
    try {
      Ok(GetInformationForLocationService.getJsonCityReport(cityName))
    } catch {
      case ioe: java.io.IOException =>  InternalServerError(ioe.toString)
      case ste: java.net.SocketTimeoutException => InternalServerError(ste.toString)
    }
  }
}
