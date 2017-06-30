package service.city

import com.google.inject.ImplementedBy
import model.Station
import play.api.libs.json._
import service.city.impl.LatLngServiceImpl


@ImplementedBy(classOf[LatLngServiceImpl])
trait LatLngService {

  def getLatLngJson(cityName: String): JsValue

  def findTheClosestStations(cityName: String): Seq[Station]

}
