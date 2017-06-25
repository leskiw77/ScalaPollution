package service

import model.{Measurement, StationMeasurement}
import play.api.libs.json.{JsResultException, Json}

object GetBasicStationInformation {

  def getMeasurementsForStation(stationId:Int) = {
    val indexLevelName:String = getIndexLevelNameForStation(stationId)
    val measumentList = for {
      sensorId <- getSensorsIdListForStation(stationId)
    }yield getLastMeasurementForSensor(sensorId)
    StationMeasurement(stationId,indexLevelName,measumentList)
  }

  private def get(url: String) = scala.io.Source.fromURL(url).mkString

  private def getSensorsIdListForStation(stationId: Int) = {
    val url = " http://api.gios.gov.pl/pjp-api/rest/station/sensors/" + stationId.toString
    val content = get(url)
    val json = Json.parse(content)
    for {
      e <- json \\ "id"
    }yield e.as[Int]
  }

  private def getIndexLevelNameForStation(stationId: Int) = {
    val url = "http://api.gios.gov.pl/pjp-api/rest/aqindex/getIndex/" + stationId.toString
    val content = get(url)
    val json = Json.parse(content)
    (json \ "stIndexLevel" \ "indexLevelName").as[String]
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
        case _:JsResultException =>
      }
      i += 1
      if(i>= max){
        return Measurement(key,0,"No measurements detected for this sensor")
      }
    }

    throw new IllegalStateException("No measurements for sensor")
  }
}
