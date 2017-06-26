package service

import model.Station
import play.api.libs.json.Json


object GetAllStationsForAirQualityService {

  def get(url: String) = scala.io.Source.fromURL(url).mkString


  def getQualityForStation(station:Station)={
    val url = "http://api.gios.gov.pl/pjp-api/rest/aqindex/getIndex/" + station.id

    val content = get(url)
    val json = Json.parse(content)
    (json \ "stIndexLevel" \ "id").as[Int]
  }

  def getStationsForAirQuality(airQuality: Int): Seq[Station] ={
    val allStations:Seq[Station] =GetAllService.getAll()
    for{
      station <- allStations
      if getQualityForStation(station) == airQuality
    }yield station
  }

}
