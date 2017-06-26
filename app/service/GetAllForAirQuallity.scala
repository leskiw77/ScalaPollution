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

  def main(args: Array[String]): Unit = {
    val st = Station(14,"Dupa",12,13)
    println(getStationsForAirQuality(2))
    println(getStationsForAirQuality(3))
    println(getStationsForAirQuality(4))
    println(getStationsForAirQuality(5))
  }
}
