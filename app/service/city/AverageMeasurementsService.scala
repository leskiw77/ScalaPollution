package service.city

import com.google.inject.ImplementedBy
import model.{Measurement, Station}
import service.city.impl.AverageMeasurementsServiceImpl


@ImplementedBy(classOf[AverageMeasurementsServiceImpl])
trait AverageMeasurementsService {

  def getAverageMeasurements(stations: Seq[Station]): (Seq[Measurement], Int)

}
