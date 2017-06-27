package service

import scala.io.Source
import scala.collection.immutable.TreeSet


class TrainingSetStats private (private val inputLines: List[String], private val targetLines:List[String], private
val seqServ : SequenceAlignmentService = SequenceAlignmentService(1, 2)) {
  if (inputLines.size != targetLines.size)
    throw new IllegalArgumentException

  val statisticService: StatisticService = StatisticService((inputLines ++ targetLines).map(_.length.toDouble))
  val mean: Double = statisticService.mean
  val std: Double = statisticService.std

  val pairs: Int = inputLines.size
  val lengthStats: Map[Int, Int] = inputLines ++ targetLines groupBy(_.length) mapValues(_.size)
  val correct: Int = inputLines.zip(targetLines).count(s => s._1 == s._2)
  val wrong: Int = pairs - correct
  val editStats: Map[Int, Int] = {
    (inputLines zip targetLines).groupBy(s => seqServ.align(s._1, s._2)).mapValues(_.size)
  }
  val editSet: Set[(Int, Int)] = editSet(Ordering.by((s: (Int, Int)) => s._1))
  def editSet(ordering: Ordering[(Int, Int)]): Set[(Int, Int)] =
    TreeSet.empty[(Int, Int)](ordering).++(editStats.iterator)
  def <= (that: TrainingSetStats) : TrainingSetStats =
    new TrainingSetStats(this.inputLines ++ that.inputLines, this.targetLines ++ that.targetLines, seqServ)
}


object TrainingSetStats {
  val f: String => List[String] = s => Source.fromFile(s).getLines().toList
  def apply(inputPath: String, targetPath: String,
            seqServ: SequenceAlignmentService = SequenceAlignmentService(1, 2)): TrainingSetStats =
    new TrainingSetStats(f(inputPath), f(targetPath), seqServ)
  def reduce(list: List[TrainingSetStats]): TrainingSetStats = list.reduce(_ <= _)
  def unit: TrainingSetStats = Unit

  private object Unit extends TrainingSetStats(List.empty, List.empty) {
    override val pairs:Int = 1
  }
}

