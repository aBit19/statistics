package service

trait SequenceAlignmentService {
  def align(seq1: String, seq2: String) : Int
}

object SequenceAlignmentService {
  def apply(gap: Int, mismatch: Int): SequenceAlignmentService = new SequenceAlignmentServiceImpl(gap, mismatch)
  val editDistance: SequenceAlignmentService = new SequenceAlignmentServiceImpl(1, 1)

  private class SequenceAlignmentServiceImpl (val penaltyGap: Int, val penaltyMismatch: Int) extends
  SequenceAlignmentService{
    def align(seq1: String, seq2: String): Int = {
      def cost(i: Int, j: Int) = if (seq1.charAt(i - 1) == seq2.charAt(j - 1)) 0 else penaltyMismatch

      val arr: Array[Array[Int]] = Array.fill(seq1.length + 1)(new Array[Int](seq2.length + 1))

      for (i <- Range(0, seq1.length + 1)) {arr(i)(0) = i * penaltyGap}
      for (j <- Range(0, seq2.length + 1)) {arr(0)(j) = j * penaltyGap}

      for (i <- Range(1, seq1.length + 1))
        for (j <- Range(1, seq2.length + 1)) {
        arr(i)(j) =
          math.min(cost(i, j) +  arr(i - 1)(j - 1), math.min(penaltyGap + arr(i - 1)(j), penaltyGap + arr(i)(j - 1)))
      }
      arr(arr.length - 1)(arr(0).length - 1)
    }

  }
}
