package service.city.impl

import javax.inject.Inject

import model.{CityReport, Station}
import play.api.libs.json._
import service.city.{AverageMeasurementsService, CityService, LatLngService}
import service.measurements.MeasurementsService
import service.station.GetAllService


class CityServiceImpl @Inject()(measService: MeasurementsService,
                                latLngService: LatLngService,
                                avMeasService: AverageMeasurementsService) extends CityService {

  def getJsonCityReport(cityToFind: String): JsValue = {
    val report = getCityReport(cityToFind)
    JsObject(Seq(
      "name" -> JsString(report.name),
      "indexLevelNumber" -> JsNumber(report.indexLevelValue),
      "indexLevelName" -> JsString(report.indexLevelName),
      "stations" -> Json.toJson(for {stationId <- report.stations} yield JsNumber(stationId.id)),
      "measurements" -> measService.getJsonSeqFromMeasurement(report.averageMeasurements)
    ))
  }

  def getCityReport(cityToFind: String): CityReport = {
    val stations = getStationsForCity(cityToFind)
    println("Stations in " + cityToFind + " : " + stations)
    val (measurements, index) = stations match {
      case Nil => avMeasService.getAverageMeasurements(latLngService.findTheClosestStations(cityToFind))
      case _ => avMeasService.getAverageMeasurements(stations)
    }
    println("Average measurements = " + measurements)
    val indexName = index match {
      case 0 => "Bardzo dobry"
      case 1 => "Dobry"
      case 2 => "Umiarkowany"
      case 3 => "Zły"
      case 4 => "Bardzo zły"
      case _ => ""
    }
    println("Average index = " + indexName)
    stations match {
      case Nil => CityReport(cityToFind, indexName, index.toInt, latLngService.findTheClosestStations(cityToFind), measurements)
      case _ => CityReport(cityToFind, indexName, index.toInt, stations, measurements)
    }
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
