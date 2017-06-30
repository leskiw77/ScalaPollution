package service.city.impl

import javax.inject.Inject

import model.{Measurement, Station}
import service.city.AverageMeasurementsService
import service.measurements.MeasurementsService
import service.station.SingleStationInfoService


class AverageMeasurementsServiceImpl @Inject()(measService: MeasurementsService,
                                               stationService: SingleStationInfoService)
  extends AverageMeasurementsService {

  def getAverageMeasurements(stations: Seq[Station]): (Seq[Measurement], Int) = {
    println("Get measurements from " + stations)
    val stMeasList = stations.map(e => stationService.getStationMeasurements(e.id))
    (measService.getAverageMeasurements(stMeasList),
      (stMeasList.map(e => e.indexLevelValue).sum.toDouble / stMeasList.length.toDouble).round.toInt)
  }

}
