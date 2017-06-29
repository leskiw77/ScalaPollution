package service.station

import model.Station
import play.api.libs.json._

import scala.collection.immutable.{List, Seq}

object GetAllService {

  private val url = "http://api.gios.gov.pl/pjp-api/rest/station/findAll"

  private def get(url: String): String = scala.io.Source.fromURL(url).mkString

  private var stationsList = List[Station]()

  def getAll: Seq[Station] = {
    if (stationsList.isEmpty) {
      val content = get(url)
      val json = Json.parse(content)
      val max = (json \\ "stationName").length
      for (i <- 0 until max) {
        val elem = json.apply(i)
        val id: Int = (elem \ "id").as[Int]
        val name: String = (elem \ "stationName").as[String]
        val city = elem \ "city"
        val cityName = city.toString match {
          case "JsDefined(null)" => ""
          case _ => (city \ "name").as[String]
        }
        val gegrLat: Double = (elem \ "gegrLat").as[String].toDouble
        val gegrLon: Double = (elem \ "gegrLon").as[String].toDouble
        val station = Station(id, name, cityName, gegrLat, gegrLon)
        stationsList = station :: stationsList
      }
    }
    stationsList
  }
}