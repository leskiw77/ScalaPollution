package service.city

import com.google.inject.ImplementedBy
import model.CityReport
import play.api.libs.json._
import service.city.impl.CityServiceImpl


@ImplementedBy(classOf[CityServiceImpl])
trait CityService {

  def getJsonCityReport(cityToFind: String): JsValue

  def getCityReport(cityToFind: String): CityReport

}
