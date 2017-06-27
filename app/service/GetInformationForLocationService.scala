package service

import model.{CityReport, Station}
import play.api.libs.json._

import scala.collection.immutable.List


object GetInformationForLocationService {

  def getJsonCityReport(cityToFind:String) = {

    val report = getCityReport(cityToFind)
    val json: JsValue = JsObject(Seq(
      "name" -> JsString(report.name),
      "indexLevelValue" -> JsNumber(report.indexLevelValue),
      "indexLevelNumber" -> JsString(report.indexLevelName),
      "stations" -> Json.toJson(for {stationId <- report.stations} yield JsNumber(stationId.id))
    ))
    json
  }

  def getCityReport(cityToFind:String) : CityReport = {
    val stations = getStationsForCity(cityToFind)
    val index_values = stations.map(getIndexLevelNumberForStation)
    val index = (index_values.sum.toDouble / index_values.length).round

    val indexName = index match {
      case 0 => "bardzo dobry"
      case 1 => "dobry"
      case 2 => "umiarkowany"
      case 3 => "zły"
      case 4 => "bardzo zły"
    }
    CityReport(cityToFind, indexName, index.toInt, stations)
  }

  private def get(url: String) = scala.io.Source.fromURL(url).mkString

  private def getStationsForCity(cityToFind:String) = {
    var stationList: List[Station] = List()
    val url = "http://api.gios.gov.pl/pjp-api/rest/station/findAll"
    val content = get(url)
    val json = Json.parse(content)

    val max = (json \\ "city").length
    for (i <- 0 until max) {

      val elem = json.apply(i)
      try {
        val city = (elem \ "city" \ "name").as[String]
        if(city == cityToFind){

          val id: Int = (elem \ "id").as[Int]
          val name: String = (elem \ "stationName").as[String]
          val gegrLat: Double = (elem \ "gegrLat").as[String].toDouble
          val gegrLon: Double = (elem \ "gegrLon").as[String].toDouble
          val station = Station(id, name, gegrLat, gegrLon)
          stationList = stationList :+ station
        }

      }catch {
        case _:Exception =>
      }
    }
    stationList
  }

  private def getIndexLevelNumberForStation(station: Station):Int = {
    val url = "http://api.gios.gov.pl/pjp-api/rest/aqindex/getIndex/" + station.id
    val content = get(url)
    val json = Json.parse(content)
    (json \ "stIndexLevel" \ "id").as[Int]
  }

}
