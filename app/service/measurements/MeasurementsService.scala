package service.measurements

import com.google.inject.ImplementedBy
import model.{Measurement, StationMeasurement}
import play.api.libs.json._
import service.measurements.impl.MeasurementsServiceImpl


@ImplementedBy(classOf[MeasurementsServiceImpl])
trait MeasurementsService {

  def getJsonSeqFromMeasurement(measurements: Seq[Measurement]): JsValue

  def getAverageMeasurements(stationsMeasurements: Seq[StationMeasurement]): Seq[Measurement]

}
