package model

case class StationMeasurement(id: Int, indexLevelName: String, indexLevelValue: Int, measurements: Seq[Measurement])

case class Measurement(key: String, lastValue: Double, dateOfLastMeasurement: String)