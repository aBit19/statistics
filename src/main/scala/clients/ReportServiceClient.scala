package clients

import service._
object ReportServiceClient {
  def main(args: Array[String]): Unit = {
    val stats = TrainingSetStats(Util.pathLang8 + "entries.dev.original", Util.pathLang8 + "entries.dev.corrected",
      SequenceAlignmentService(4, 2))
    val stats1 = TrainingSetStats(Util.pathTest + "input.txt", Util.pathTest + "targetValid.txt")
    val reportService: ReportService = st => "mean: %f\nstd: %f\nedit [20-30]: %s".format(st.mean, st.std, st.editSet
      .filter(s => s._1 >= 0 && s._1 < 20).toString())
    print(reportService(stats))
    print(reportService(stats1))
  }
}
