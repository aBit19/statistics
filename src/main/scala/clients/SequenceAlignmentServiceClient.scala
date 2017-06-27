package clients

import service.SequenceAlignmentService

object SequenceAlignmentServiceClient {
  def main(args: Array[String]): Unit = {
    val alignmentService = SequenceAlignmentService(1, 1)
    println(alignmentService.align("kitten", "sitting"))
  }
}
