package service

import model.Station
import play.api.libs.json._


object GetAllStationsForAirQualityService {

  def getStationsIdJsonForAirQuality(airQuality: Int): JsValue = {
    val stations = getStationsForAirQuality(airQuality)
    val idList = for {station <- stations} yield JsNumber(station.id)
    Json.toJson(idList)
  }

  def getStationsForAirQuality(airQuality: Int): Seq[Station] ={
    val allStations:Seq[Station] = GetAllService.getAll()
    for{
      station <- allStations
      if getQualityForStation(station) == airQuality
    }yield station
  }

  private def get(url: String) = scala.io.Source.fromURL(url).mkString

  private def getQualityForStation(station:Station)={
    val url = "http://api.gios.gov.pl/pjp-api/rest/aqindex/getIndex/" + station.id
    val content = get(url)
    val json = Json.parse(content)
    (json \ "stIndexLevel" \ "id").as[Int]
  }

  def main(args: Array[String]): Unit = {
    val x = GetAllStationsForAirQualityService.getStationsIdJsonForAirQuality(1)
    println(x.getClass)
    println(x(1).getClass)
  }

}
