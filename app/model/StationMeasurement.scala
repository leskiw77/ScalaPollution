package model

import play.api.libs.json._


case class StationMeasurement(id:Int, indexLevelName:String, indexLevelValue:Int, measurements: Seq[Measurement])
case class Measurement(key:String, lastValue:Double, dateOfLastMeasurement:String)
/*{
  implicit val stationWrites = new Writes[StationMeasurement] {
    def writes(station: StationMeasurement) = Json.obj(
      "id" -> station.id,
      "indexLevelName" -> station.indexLevelName,
      "measurements" -> "2"
    )
  }

  implicit val measurementWrites = new Writes[Measurement] {
    def writes(measurement: Measurement) = Json.obj(
      "key" -> measurement.key,
      "lastValue" -> measurement.lastValue,
      "dateOfLastMeasurement" -> measurement.dateOfLastMeasurement
    )
  }

  def getJson() = {
    Json.toJson(StationMeasurement(id,indexLevelName,measurements))
  }
}
*/

/*
{

  def getJson() = {
    Json.toJson(Measurement(key,lastValue,dateOfLastMeasurement))
  }

  implicit val measurementWrites = new Writes[Measurement] {
    def writes(measurement: Measurement) = Json.obj(
      "key" -> measurement.key,
      "lastValue" -> measurement.lastValue,
      "dateOfLastMeasurement" -> measurement.dateOfLastMeasurement
    )
  }
}

object Measurement{

  /*
  implicit val measurementSeqWrites = new Writes[Seq[model.Measurement]] {
    def writes(measurements: Seq[Measurement]): JsValue = {
      for{
        measurement <- measurements
      }yield Json.toJson(measurement)
    }
  }
*/

  def main(args: Array[String]): Unit = {
    val l = List(Measurement("NO2", 2.2,"2017"), Measurement("CO2", 12.2,"2016"))
    print(StationMeasurement(12, "super", l).getJson())

  }
}
*/
