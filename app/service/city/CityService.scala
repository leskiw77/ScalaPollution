package service.city

import model.{CityReport, Station}
import play.api.libs.json._
import service.measurements.MeasurementsService
import service.station.GetAllService


object CityService {

  def getJsonCityReport(cityToFind: String): JsValue = {
    val report = getCityReport(cityToFind)
    JsObject(Seq(
      "name" -> JsString(report.name),
      "indexLevelValue" -> JsNumber(report.indexLevelValue),
      "indexLevelNumber" -> JsString(report.indexLevelName),
      "stations" -> Json.toJson(for {stationId <- report.stations} yield JsNumber(stationId.id)),
      "measurements" -> MeasurementsService.getJsonSeqFromMeasurement(report.averageMeasurements)
    ))
  }

  def getCityReport(cityToFind: String): CityReport = {
    val stations = getStationsForCity(cityToFind)
    println("Stations in "+cityToFind+" : "+stations)
    val (measurements,index) = stations match {
      case Nil => AverageMeasurementsService.getAverageMeasurements(LatLngService.findTheClosestStations(cityToFind))
      case _ => AverageMeasurementsService.getAverageMeasurements(stations)
    }
    println("Average measurements = "+measurements)
    val indexName = index match {
      case 0 => "bardzo dobry"
      case 1 => "dobry"
      case 2 => "umiarkowany"
      case 3 => "zły"
      case 4 => "bardzo zły"
      case _ => ""
    }
    CityReport(cityToFind, indexName, index.toInt, stations, measurements)
  }

  private def get(url: String) = scala.io.Source.fromURL(url).mkString

  private def getStationsForCity(cityToFind: String) = {
    GetAllService.getAll.filter(e => e.cityName == cityToFind)
  }

  private def getIndexLevelNumberForStation(station: Station): Int = {
    val url = "http://api.gios.gov.pl/pjp-api/rest/aqindex/getIndex/" + station.id
    val content = get(url)
    val json = Json.parse(content)
    (json \ "stIndexLevel" \ "id").as[Int]
  }

}
