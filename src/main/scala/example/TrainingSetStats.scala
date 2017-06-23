package example

import scala.io.Source

class TrainingSetStats(private val inputLines: List[String], private val targetLines:List[String]) {
  if (inputLines.size != targetLines.size)
    throw new IllegalArgumentException
  val pairs: Int = inputLines.size
  val getSentenceLengthStats: Map[Int, Int] = {
    (inputLines.groupBy(s => s.length) ++ targetLines.groupBy(s => s.length)).mapValues(s => s.size)
  }
  val mean: Double = (inputLines ++ targetLines).map(_.length).sum / (pairs * 2)
  val std: Double = Math.sqrt((inputLines ++ targetLines).map(_.length).map(s => Math.pow(s - mean, 2)).sum / (pairs * 2))
  val correct: Int = inputLines.zip(targetLines).count(s => s._1 == s._2)
  val wrong: Int = pairs - correct

  def <= (that: TrainingSetStats) : TrainingSetStats =
    new TrainingSetStats(this.inputLines ++ that.inputLines, this.targetLines ++ that.targetLines)
}

object TrainingSetStats {
  val f: String => List[String] = s => Source.fromFile(s).getLines().toList
  def apply(inputPath: String, targetPath: String): TrainingSetStats =
    new TrainingSetStats(f(inputPath), f(targetPath))
}
