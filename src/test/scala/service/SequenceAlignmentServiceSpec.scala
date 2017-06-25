package service

import org.scalatest.{BeforeAndAfterEach, FlatSpec}

class SequenceAlignmentServiceSpec extends FlatSpec with BeforeAndAfterEach {

  private val gap = 1
  private val mis = 2
  private val sequenceAlignmentService = SequenceAlignmentService(gap, mis)

  "A SequenceAlignmentService" should "calculate the minimum alignment between two strings" in {
    sequenceAlignmentService.align("i am not here", "i am here")
  }
  it should "return 0 for same sequences" in {
    assert(sequenceAlignmentService.align("i am here", "i am here") == 0)
  }

  it should "return penalty gap as minimum alignment for sequences with a gap difference in the start of the " +
    "sentence" in {
    assert(sequenceAlignmentService.align(" i am here", "i am here") == gap)
  }

  it should "return 4 for min alignment between mean and name" in {
    assert(sequenceAlignmentService.align("mean", "name") == 4)
  }

  it should "return 0 for empty strings" in {
    assert(sequenceAlignmentService.align("", "") == 0)
  }
}
