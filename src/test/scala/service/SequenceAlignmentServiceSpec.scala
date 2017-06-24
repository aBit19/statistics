package service

import org.scalatest.{BeforeAndAfterEach, FlatSpec}

class SequenceAlignmentServiceSpec extends FlatSpec with BeforeAndAfterEach {

  private val gap = 1
  private val mis = 2
  private val sequenceAlignmentService = SequenceAlignmentService(gap, mis)

  "A SequenceAlignmentService" should "accept a Int for penalty gap and a Int for mismatch" in {
    val gap = 2
    val mismatch = 4
    val edit = SequenceAlignmentService(gap, mismatch)
    assert(edit.penaltyGap == gap)
    assert(edit.penaltyMismatch == mismatch)
  }

  it should "calculate the minimum alignment between two strings" in {
    sequenceAlignmentService.align("i am not here", "i am here")
  }
  it should "return 0 for same sequences" in {
    assert(sequenceAlignmentService.align("i am here", "i am here") == 0)
  }

  it should "return penalty gap as minimum alignment for sequences with a gap difference in the start of the " +
    "sentence" in {
    assert(sequenceAlignmentService.align(" i am here", "i am here") == 1)
  }

}
