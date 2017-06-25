package clients

import service.{SequenceAlignmentService, TrainingSetStats}

object TrainingSetStatsClient {

  def main(args: Array[String]): Unit = {
    testClient()
    lang8Client()
    editClient()
  }
  private def testClient(): Unit = {
    val stats = TrainingSetStats(this >> "entries.train.original", this >> "entries.train.corrected",
      SequenceAlignmentService(5, 3))
    print(stats.editSet.max(scala.math.Ordering.by((s: (Int, Int)) => s._2))._2 / (stats.pairs * 1.0))
  }

  private def lang8Client(): Unit = {
    val lang8Train = TrainingSetStats(>> ("entries.train.original"), >>("entries.train.corrected"))
    val lang8Test = TrainingSetStats(>> ("entries.test.original"), >> ("entries.test.corrected"))
    val lang8Dev = TrainingSetStats(>> ("entries.dev.original"), >> ("entries.dev.corrected"))
    val lang8corpus = TrainingSetStats.reduce(List(lang8Train, lang8Test, lang8Dev))
  }

  private def editClient(from: Int = 20, to: Int = 40): Unit = {
    import scala.math.Ordering
    val langTest = TrainingSetStats(>> ("entries.test.original"), >> ("entries.test.corrected"))
    println("Edit Distance\t\tNumber of pairs")
    val ordering: Ordering[(Int, Int)] = Ordering[(Int, Int)].on(x => (x._1, x._2))
    langTest.editSet(ordering).foreach(println(_))
  }

  private def >>(file: String) : String = Util.pathLang8 + file
}
