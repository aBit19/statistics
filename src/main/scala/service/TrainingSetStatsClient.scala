package service

object TrainingSetStatsClient {
  import java.io.File
  val path = new File(".").getAbsoluteFile+"/data/lang-8-en-1.0/"

  def main(args: Array[String]): Unit = {
    val lang8Train = TrainingSetStats(>> ("entries.train.original"), >>("entries.train.corrected"))
    val lang8Test = TrainingSetStats(>> ("entries.test.original"), >> ("entries.test.corrected"))
    val lang8Dev = TrainingSetStats(>> ("entries.dev.original"), >> ("entries.dev.corrected"))
    val lang8corpus = TrainingSetStats.reduce(List(lang8Train, lang8Test, lang8Dev))
    print(lang8Train.report)
    print(lang8Test.report)
    print(lang8Dev.report)
  }

  def >>(file: String) : String = path + file
}
