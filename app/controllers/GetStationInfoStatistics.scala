package controllers

import controllers.GetAllController.{InternalServerError, Ok}
import play.api.mvc.Action
import service.{GetAllService, GetBasicStationInformation}

/**
  * Created by jarema on 6/25/17.
  */
object GetStationInfoStatistics {

  def getStationInfo(id:Int) = Action{
    try {
      Ok(GetBasicStationInformation.getMeasurementsForStation(id))
    } catch {
      case ioe: java.io.IOException =>  InternalServerError(ioe.toString)
      case ste: java.net.SocketTimeoutException => InternalServerError(ste.toString)
    }
  }
}
