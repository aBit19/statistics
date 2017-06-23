package example

import java.io.File

import org.scalatest._

class TrainingSetStatsSpec extends FlatSpec with BeforeAndAfterEach {
  private val path = new File(".").getCanonicalFile + "/data/"
  private val stats = TrainingSetStats(path + "input.txt", path + "targetValid.txt")


  "A TrainingStats" should "accept two files 1) input sentence and 2) target sentence respectively " in {
    val trainingSetStatsSpec = TrainingSetStats(path + "input.txt", path + "input.txt")
  }

  it should "throw IllegalArgumentException in case the file have different number of lines" in {
    assertThrows[IllegalArgumentException]{
      val trainingSetStats = TrainingSetStats(path + "input.txt", path + "target.txt")
    }
  }

  it should "return number of sentence pairs" in {
    assert(stats.pairs == 4)
  }

  it should "return statistics in Map object with the sentence length as key" in {
    val map: Map[Int, Int] = stats.getSentenceLengthStats
  }

  it should "return 4 sentences with length 14" in {
    assert(stats.getSentenceLengthStats.get(14).contains(4))
  }

  it should "return the mean of the sentence length" in {
    assert(stats.mean == 14)
  }

  it should "return the stdv of the sentence length" in {
    assert(stats.std == 0)
  }

  it should "return the number of correctinput" in {
    assert(stats.correct == 4)
  }

  it should "return the number of errored" in {
    assert(stats.wrong == 0)
  }

  it can "combine with another Stat" in {
    val that = TrainingSetStats(path + "input.txt", path + "targetValid.txt")
    val combine: TrainingSetStats = stats <= that
    assert(combine.pairs == 2 * stats.pairs)
    assert(combine.wrong == that.wrong * 2)
    assert(combine.correct == that.correct * 2)
    assert(combine.std == stats.std)
    assert(combine.mean == combine.mean)
  }

  it can "process lang8 data" in {
    val lang8 = TrainingSetStats(path + "entries.test.original", path + "entries.test.corrected")
  }

}
