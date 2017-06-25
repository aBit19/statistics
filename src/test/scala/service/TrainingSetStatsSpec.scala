package service

import java.io.File

import org.scalatest._

class TrainingSetStatsSpec extends FlatSpec {
  private val path = new File(".").getCanonicalFile + "/data/"
  private val stats = TrainingSetStats( this >> "input.txt",  this >> "targetValid.txt")


  "A TrainingSetStats" should "accept two files 1) input sentence and 2) target sentence respectively " in {
    val trainingSetStatsSpec = TrainingSetStats( this >> "input.txt", this >> "input.txt")
  }

  it should "throw IllegalArgumentException in case the files have different number of lines" in {
    assertThrows[IllegalArgumentException] {
      TrainingSetStats(this >> "input.txt", this >> "target.txt")
    }
  }

  it should "return number of sentence pairs" in {
    assert(stats.pairs == 4)
  }

  it should "return statistics in Map object with the sentence length as key" in {
    val map: Map[Int, Int] = stats.lengthStats
  }

  it should "return 8 sentences with length 14" in {
    assert(stats.lengthStats.get(14).contains(8))
  }

  it should "return the mean of the sentence length" in {
    assert(stats.mean == 14)
  }

  it should "return the stdv of the sentence length" in {
    assert(stats.std == 0)
  }

  it should "return the number of correct input" in {
    assert(stats.correct == 4)
  }

  it should "return the number of wrong sentences" in {
    assert(stats.wrong == 0)
  }

  it can "combine with another Stat" in {
    val that = TrainingSetStats(this >> "input.txt", this >> "targetValid.txt")
    val combine: TrainingSetStats = stats <= that
    assert(combine.pairs == 2 * stats.pairs)
    assert(combine.wrong == that.wrong * 2)
    assert(combine.correct == that.correct * 2)
    assert(combine.std == stats.std)
    assert(combine.mean == combine.mean)
  }

  it can "process lang8 data" in {
    val lang8 = TrainingSetStats(this >> "/lang-8-en-1.0/entries.test.original",
      this >> "/lang-8-en-1.0/entries.test.corrected")
  }

  "The <= operation" should "be commutative" in {
    val that = TrainingSetStats(this  >> "inputCombine.txt", this >> "targetCombine.txt")
    val combination1 = stats <= that
    val combination2 = that <= stats
    assert(combination1.pairs == combination2.pairs)
    assert(combination1.wrong == combination2.wrong)
    assert(combination1.mean == combination2.mean)
    assert(combination1.std == combination2.std)
  }

  it should "be associative" in {
    val other = TrainingSetStats(this >> "input.txt", this >> "input.txt")
    val other1 = TrainingSetStats(this >> "inputCombine.txt", this >> "targetCombine.txt")
    val op1 = stats <= other <= other1
    val op2 = stats <= (other <= other1)
    assert(op1.pairs == op2.pairs)
    assert(op1.wrong == op2.wrong)
    assert(op1.mean == op2.mean)
    assert(op1.std == op2.std)
  }

  "The companion object" should "reduce a list of statistic object to one" in {
    TrainingSetStats.reduce(List(stats, TrainingSetStats(this >> "inputCombine.txt", this >> "targetCombine.txt")))
  }

  it should "return a unit object" in {
    val unit = TrainingSetStats.unit
    val res = unit <= stats
    assert(res.pairs == stats.pairs)
    assert(res.wrong == stats.wrong)
    assert(res.mean == stats.mean)
    assert(res.std == stats.std)
  }

  it should "accept SequenceAlignmentService" in {
    val align = SequenceAlignmentService(1, 2)
    TrainingSetStats(this >> "input.txt", this >> "input.txt", align)
  }

  "A TrainingSetStats" should "return the statistics of edit distance in a map with edit distance as key" in {
    stats.editStats.iterator.next()._1 match {
      case _ : Int => succeed
      case _ => fail
    }
  }

  it should "return that there are 4 tuples with edit distance 0" in {
    assert(stats.editStats.get(0).contains(4))
  }

  it should "return a set with all (edit, number of pairs) in order by edit" in {
    assert(stats.editSet.iterator.next()._1 == 0)
  }

  it should "be able to accept an ordering strategy for the (edit, number) set" in {
    import math.Ordering
    val ordering: Ordering[(Int, Int)] = Ordering.by(_._2)
    val set: Set[(Int, Int)] = stats.editSet(ordering)
    assert(set.size == 1 && set.iterator.next()._2 == 4)
  }

  private def >>(file: String):String = path + file
}
