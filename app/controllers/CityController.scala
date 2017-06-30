package controllers

import javax.inject.Inject

import play.api.mvc.{Action, _}
import service.city.{CityService, LatLngService}


class CityController @Inject()(cityService: CityService, latLngService: LatLngService) extends Controller {

  def getCityAverageMeasurements(cityName: String) = Action {
    try {
      Ok(cityService.getJsonCityReport(cityName))
    } catch {
      case ioe: java.io.IOException => InternalServerError(ioe.toString)
      case ste: java.net.SocketTimeoutException => InternalServerError(ste.toString)
    }
  }

  def getCityInfo(cityName: String) = Action {
    try {
      Ok(cityService.getJsonCityReport(cityName))
    } catch {
      case ioe: java.io.IOException => InternalServerError(ioe.toString)
      case ste: java.net.SocketTimeoutException => InternalServerError(ste.toString)
    }
  }

  def getCityLanLng(cityName: String) = Action {
    try {
      Ok(latLngService.getLatLngJson(cityName))
    } catch {
      case ioe: java.io.IOException => InternalServerError(ioe.toString)
      case ste: java.net.SocketTimeoutException => InternalServerError(ste.toString)
    }
  }

}
