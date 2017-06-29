package model

case class StationMeasurement(id: Int,
                              indexLevelName: String,
                              indexLevelValue: Int,
                              measurements: Seq[Measurement])