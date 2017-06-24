package service

import scala.io.Source

class TrainingSetStats private (private val inputLines: List[String], private val targetLines:List[String]) {
  if (inputLines.size != targetLines.size)
    throw new IllegalArgumentException

  val pairs: Int = inputLines.size
  val lengthStats: Map[Int, Int] = inputLines.groupBy(_.length) ++ targetLines.groupBy(_.length) mapValues(_.size)
  val mean: Double = (inputLines ++ targetLines).map(_.length).sum * 1.0 / (pairs * 2)
  val std: Double = Math.sqrt((inputLines ++ targetLines).map(_.length).map(s => Math.pow(s - mean, 2)).sum / (pairs * 2))
  val correct: Int = inputLines.zip(targetLines).count(s => s._1 == s._2)
  val wrong: Int = pairs - correct
  val report: String = "\nStatistic report\n" +
    "pairs: "+pairs+"\n" +
    "mean: "+mean+"\n" +
    "std: "+std+"\n" +
    "corrected: "+correct+"\n"+
    "wrong: "+wrong+"\n"+
    "Statistic end\n"

  def <= (that: TrainingSetStats) : TrainingSetStats =
    new TrainingSetStats(this.inputLines ++ that.inputLines, this.targetLines ++ that.targetLines)
}


object TrainingSetStats {
  val f: String => List[String] = s => Source.fromFile(s).getLines().toList
  def apply(inputPath: String, targetPath: String): TrainingSetStats =
    new TrainingSetStats(f(inputPath), f(targetPath))
  def reduce(list: List[TrainingSetStats]): TrainingSetStats = list.reduce(_ <= _)
  def unit: TrainingSetStats = Unit

  private object Unit extends TrainingSetStats(List.empty, List.empty) {
    override val pairs:Int = 1
  }
}

