package service.station

import com.google.inject.ImplementedBy
import model.StationMeasurement
import play.api.libs.json._
import service.station.impl.SingleStationInfoServiceImpl


@ImplementedBy(classOf[SingleStationInfoServiceImpl])
trait SingleStationInfoService {

  def getStationMeasurementJson (stationId: Int): JsValue

  def getStationMeasurements (stationId: Int) : StationMeasurement

}
