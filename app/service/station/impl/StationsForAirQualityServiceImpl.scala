package service.station.impl

import model.Station
import play.api.libs.json._
import service.station.{GetAllService, StationsForAirQualityService}


class StationsForAirQualityServiceImpl extends StationsForAirQualityService {

  def getStationsIdJsonForAirQuality(airQualityIndex: Int): JsValue = {
    val stations = getStationsForAirQuality(airQualityIndex)
    val idList = for {station <- stations} yield JsNumber(station.id)
    Json.toJson(idList)
  }

  def getStationsForAirQuality(airQuality: Int): Seq[Station] = {
    for {
      station <- GetAllService.getAll
      if getQualityForStation(station) == airQuality
    } yield station
  }

  private def get(url: String) = scala.io.Source.fromURL(url).mkString

  private def getQualityForStation(station: Station) = {
    val url = "http://api.gios.gov.pl/pjp-api/rest/aqindex/getIndex/" + station.id
    val content = get(url)
    val json = Json.parse(content)
    println("Checking quality from " + station.stationName)
    (json \ "stIndexLevel" \ "id").as[Int]
  }

}
