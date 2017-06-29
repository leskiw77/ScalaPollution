package model

case class CityReport(name: String,
                      indexLevelName: String,
                      indexLevelValue: Int,
                      stations: Seq[Station],
                      averageMeasurements: Seq[Measurement])