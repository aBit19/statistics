package service

class StatisticService(seq: List[Double]) {
  private val size = if (seq.isEmpty) 1 else seq.size
  val mean: Double = seq.sum / size
  val std: Double = Math.sqrt(seq.map(s => Math.pow(s - mean, 2)).sum / size)
}

object StatisticService {
  def apply(seq: List[Double]): StatisticService = new StatisticService(seq)
}


