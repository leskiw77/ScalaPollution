package service.measurements

import model.{Measurement, StationMeasurement}
import play.api.libs.json._

object MeasurementsService {

  def getJsonSeqFromMeasurement(measurements: Seq[Measurement]): JsValue = {
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

  def getAverageMeasurements(stationsMeasurements: Seq[StationMeasurement]): Seq[Measurement] = {
    var mapOfSums : Map[String, Double] = Map()
    var mapOfLens : Map[String, Int] = Map()
    stationsMeasurements.foreach( stationsMeasurement => {
      stationsMeasurement.measurements.foreach( m =>
        if(mapOfSums.contains(m.key)) {
          mapOfSums += (m.key -> (m.lastValue + mapOfSums(m.key)))
          mapOfLens += (m.key -> (1 + mapOfLens(m.key)))
        } else {
          mapOfSums += (m.key -> m.lastValue)
          mapOfLens += (m.key -> 1)
        }
      )
    })
    for(k <- mapOfSums.keySet.toSeq) yield { Measurement(k, mapOfSums(k) / mapOfLens(k).toDouble, "")}
  }

}
