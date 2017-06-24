package service

object SequenceAlignmentServiceClient {
  def main(args: Array[String]): Unit = {
    val alignmentService = SequenceAlignmentService(1, 2)
    alignmentService.align("name", "means")
  }

}
