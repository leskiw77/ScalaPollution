package controllers

import play.api.mvc._
import play.api.mvc.Action
import service.CityInformationService
import service.LatLngService

object CityController extends Controller {

  def getCityInfo(cityName: String) = Action {
    try {
      Ok(CityInformationService.getJsonCityReport(cityName))
    } catch {
      case ioe: java.io.IOException => InternalServerError(ioe.toString)
      case ste: java.net.SocketTimeoutException => InternalServerError(ste.toString)
    }
  }

  def getCityLanLng(cityName: String) = Action {
    try {
      Ok(LatLngService.getLatLngJson(cityName))
    } catch {
      case ioe: java.io.IOException => InternalServerError(ioe.toString)
      case ste: java.net.SocketTimeoutException => InternalServerError(ste.toString)
    }
  }

}
