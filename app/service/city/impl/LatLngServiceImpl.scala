package service.city.impl

import model.Station
import play.api.libs.json._
import service.city.LatLngService
import service.station.GetAllService


class LatLngServiceImpl extends LatLngService {

  private val baseUrl = "https://maps.googleapis.com/maps/api/geocode/json"

  private val key = "AIzaSyBprbHpoFBP5XN1jmLR3BTNAeYyHt1x9oY"

  private def createUrl(address: String) = baseUrl + "?address=" + address.replace(' ', '+') + "&key=" + key

  private def get(url: String): String = scala.io.Source.fromURL(url).mkString

  def getLatLngJson(cityName: String): JsValue = {
    val (lat, lng) = getLatLng(cityName)
    JsObject(Seq(
      "lat" -> JsNumber(lat),
      "lng" -> JsNumber(lng)
    ))
  }

  private def getLatLng(cityName: String): (Double, Double) = {
    val googleAPIResult = get(createUrl(cityName))
    val json = Json.parse(googleAPIResult)
    val latlng = (json \\ "location").head
    println("Lat,lng = "+latlng)
    ((latlng \ "lat").as[Double], (latlng \ "lng").as[Double])
  }

  def findTheClosestStations(cityName: String): Seq[Station] = {
    val x0y0 = getLatLng(cityName)
    GetAllService.getAll.map(s =>
      (s, Math.sqrt(Math.pow(s.geogrLat - x0y0._1, 2.0) + Math.pow(s.geogrLng - x0y0._2, 2.0)))).
      sortWith(_._2 < _._2).take(4).map(_._1)
  }

}
