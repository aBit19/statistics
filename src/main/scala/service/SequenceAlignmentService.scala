package service

class SequenceAlignmentService private (val penaltyGap: Int, val penaltyMismatch: Int) {
  def align(seq1: String, seq2: String): Int = if (seq1 == seq2) 0 else if (seq1.trim == seq2.trim) math.abs(seq1
    .length - seq2.length) else 100
}

object SequenceAlignmentService {
  def apply(gap: Int, mismatch: Int): SequenceAlignmentService = new SequenceAlignmentService(gap, mismatch)
}
