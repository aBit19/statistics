package service

import scala.collection.immutable.TreeSet

class TrainingStatisticsService private (training: List[(String, String)], alignmentService: SequenceAlignmentService) {

  val lengthStats: Map[Int, Int] =  {
    val zipped = training.unzip
    zipped._2 ++ zipped._1 groupBy(_.length) mapValues(_.size)
  }
  val correct: Int = training.count(s => s._1 == s._2)
  val wrong: Int = training.size - correct
  val editStats: Map[Int, Int] = {
    training.groupBy(s => alignmentService.align(s._1, s._2)).mapValues(_.size)
  }
  val editSet: Set[(Int, Int)] = editSet(Ordering.by((s: (Int, Int)) => s._1))
  def editSet(ordering: Ordering[(Int, Int)]): Set[(Int, Int)] =
    TreeSet.empty[(Int, Int)](ordering).++(editStats.iterator)
}

object TrainingStatisticsService {
  def apply(training: List[(String, String)],
            seqServ: SequenceAlignmentService = SequenceAlignmentService(1, 1)): TrainingStatisticsService
  = new TrainingStatisticsService(training, seqServ)
}
