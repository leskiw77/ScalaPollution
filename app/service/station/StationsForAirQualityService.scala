package service.station

import com.google.inject.ImplementedBy
import model.Station
import play.api.libs.json._
import service.station.impl.StationsForAirQualityServiceImpl


@ImplementedBy(classOf[StationsForAirQualityServiceImpl])
trait StationsForAirQualityService {

  def getStationsIdJsonForAirQuality(airQualityIndex: Int): JsValue

  def getStationsForAirQuality(airQuality: Int): Seq[Station]

}
