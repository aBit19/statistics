package example

import java.io.File

import org.scalatest._

class TrainingSetStatsSpec extends FlatSpec {
  private val path = new File(".").getCanonicalFile + "/data/"

  "A TrainingStats" should "accept two files 1) input sentence and 2) target sentence respectively " in {
    val trainingSetStatsSpec = TrainingSetStats(path + "input.txt", path + "input.txt")
  }

  it should "throw IllegalArgumentException in case the file have different lines of code" in {
    assertThrows[IllegalArgumentException]{
      val trainingSetStats = TrainingSetStats(path + "input.txt", path + "target.txt")
    }
  }

  it should "return number of sentence pairs" in {
    val trainingSetStats = TrainingSetStats(path + "input.txt",  path + "targetValid.txt")
    assert(trainingSetStats.pairs == 4)
  }

}
