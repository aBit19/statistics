package service

import scala.io.Source


class TrainingSetStats private (private val inputLines: List[String], private val targetLines:List[String], private
val seqServ : SequenceAlignmentService = SequenceAlignmentService(1, 2)) {
  if (inputLines.size != targetLines.size)
    throw new IllegalArgumentException

  val statisticService: StatisticService = StatisticService((inputLines ++ targetLines).map(_.length.toDouble))
  val mean: Double = statisticService.mean
  val std: Double = statisticService.std

  val trainingStatisticsService = TrainingStatisticsService(inputLines zip targetLines, seqServ)
  val pairs: Int = inputLines.size
  val lengthStats: Map[Int, Int] = trainingStatisticsService.lengthStats
  val correct: Int = trainingStatisticsService.correct
  val wrong: Int = pairs - correct
  val editStats: Map[Int, Int] = trainingStatisticsService.editStats
  val editSet: Set[(Int, Int)] = trainingStatisticsService.editSet
  def editSet(ordering: Ordering[(Int, Int)]): Set[(Int, Int)] = trainingStatisticsService.editSet(ordering)

  def <= (that: TrainingSetStats) : TrainingSetStats =
    new TrainingSetStats(this.inputLines ++ that.inputLines, this.targetLines ++ that.targetLines, seqServ)
}


object TrainingSetStats {
  val f: String => List[String] = s => Source.fromFile(s).getLines().toList
  def apply(inputPath: String, targetPath: String,
            seqServ: SequenceAlignmentService = SequenceAlignmentService(1, 2)): TrainingSetStats =
    new TrainingSetStats(f(inputPath), f(targetPath), seqServ)
  def combine(list: List[TrainingSetStats]): TrainingSetStats = list.reduce(_ <= _)
  def unit: TrainingSetStats = Unit

  private object Unit extends TrainingSetStats(List.empty, List.empty) {
    override val pairs:Int = 1
  }
}

