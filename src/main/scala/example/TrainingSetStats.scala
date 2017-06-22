package example

import scala.io.Source

case class TrainingSetStats(inputFile: String, targetFile: String) {
  private val inputLines = Source.fromFile(inputFile).getLines().toList
  private val targetLines = Source.fromFile(targetFile).getLines().toList

  if (inputLines.size != targetLines.size)
    throw new IllegalArgumentException()

  val pairs = inputLines.size

}
