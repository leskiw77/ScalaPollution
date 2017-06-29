package service.city

import model.{Measurement, Station, StationMeasurement}
import service.measurements.MeasurementsService
import service.station.SingleStationInfoService

object AverageMeasurementsService {

  def getAverageMeasurements(stations: Seq[Station]): (Seq[Measurement], Int) = {
    println("Get measurements from "+stations)
    val stMeasList = stations.map(e => SingleStationInfoService.getStationMeasurements(e.id))
    (MeasurementsService.getAverageMeasurements(stMeasList),
      (stMeasList.map(e => e.indexLevelValue).sum.toDouble / stMeasList.length.toDouble).round.toInt)
  }

}
