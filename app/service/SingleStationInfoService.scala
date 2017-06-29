package service

import model.{Measurement, StationMeasurement}
import play.api.libs.json._

object SingleStationInfoService {

  def getMeasurementsForStation(stationId: Int): JsValue = {
    val (indexLevelNum, indexLevelName) = getIndexLevelNameForStation(stationId)
    val measurementList = for {
      sensorId <- getSensorsIdListForStation(stationId)
    } yield getLastMeasurementForSensor(sensorId)
    getJsonFromStationMeasurement(StationMeasurement(stationId, indexLevelName, indexLevelNum, measurementList))
  }

  private def getJsonFromStationMeasurement(station: StationMeasurement) = {
    JsObject(Seq(
      "id" -> JsNumber(station.id),
      "indexLevelNumber" -> JsNumber(station.indexLevelValue),
      "indexLevelName" -> JsString(station.indexLevelName),
      "measurements" -> getJsonSeqFromMeasurement(station.measurements)
    ))
  }

  private def get(url: String) = scala.io.Source.fromURL(url).mkString

  private def getJsonSeqFromMeasurement(measurements: Seq[Measurement]) = {
    Json.toJson(for {
      measurement <- measurements
    } yield {
      JsObject(Seq(
        "key" -> JsString(measurement.key),
        "lastValue" -> JsNumber(measurement.lastValue),
        "dateOfLastMeasurement" -> JsString(measurement.dateOfLastMeasurement)
      ))
    })
  }

  private def getSensorsIdListForStation(stationId: Int) = {
    val url = " http://api.gios.gov.pl/pjp-api/rest/station/sensors/" + stationId.toString
    val content = get(url)
    val json = Json.parse(content)
    for {
      e <- json \\ "id"
    } yield e.as[Int]
  }

  private def getIndexLevelNameForStation(stationId: Int) = {
    val url = "http://api.gios.gov.pl/pjp-api/rest/aqindex/getIndex/" + stationId.toString
    val content = get(url)
    val json = Json.parse(content)
    ((json \ "stIndexLevel" \ "id").as[Int], (json \ "stIndexLevel" \ "indexLevelName").as[String])
  }

  private def getLastMeasurementForSensor(sensorId: Int): Measurement = {
    val url = "http://api.gios.gov.pl/pjp-api/rest/data/getData/" + sensorId.toString
    val content = get(url)
    val json = Json.parse(content)

    val key = (json \ "key").as[String]
    val values = json \ "values"

    val max = (json \\ "value").length

    var i = 0
    while (true) {
      val elem = values.apply(i)
      try {
        val lastValue = (elem \ "value").as[Double]
        val lastDate = (elem \ "date").as[String]
        return Measurement(key, lastValue, lastDate)
      }
      catch {
        case _: JsResultException =>
      }
      i += 1
      if (i >= max) {
        return Measurement(key, 0, "No measurements detected for this sensor")
      }
    }
    throw new IllegalStateException("No measurements for sensor")
  }

}
