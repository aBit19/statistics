package clients

import service.SequenceAlignmentService

object SequenceAlignmentServiceClient {
  def main(args: Array[String]): Unit = {
    val alignmentService = SequenceAlignmentService(1, 2)
    println(alignmentService.align("name", "mean"))
    println(alignmentService.align("name", "name"))
  }
}
