package service

  import model.Station
  import play.api.libs.json.Json

  import scala.collection.immutable.{List, Seq}

/**
  * Created by jarema on 6/25/17.
  */
object GetAllService {

  val url = "http://api.gios.gov.pl/pjp-api/rest/station/findAll"

  def get(url: String) = scala.io.Source.fromURL(url).mkString

  def getAll(): Seq[Station] = {
    var stationList: List[Station] = List()

    val content = get(url)
    val json = Json.parse(content)

    val max = (json \\ "stationName").length
    for (i <- 0 until max) {
      val elem = json.apply(i)
      val id: Int = (elem \ "id").as[Int]
      val name: String = (elem \ "stationName").as[String]
      val gegrLat: Double = (elem \ "gegrLat").as[String].toDouble
      val gegrLon: Double = (elem \ "gegrLon").as[String].toDouble
      val station = Station(id, name, gegrLat, gegrLon)
      stationList = stationList :+ station
    }
    stationList
  }
}