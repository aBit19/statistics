package service

import org.scalatest.FlatSpec

class ReportServiceSpec extends FlatSpec {
  import java.io.File

  private val path = new File(".").getCanonicalFile + "/data/"
  private val stats = TrainingSetStats( path + "input.txt",  path + "targetValid.txt")

  "A ReportService" should "accept TrainingSetStats object" in {
    val reportService: ReportService = t => {
      "mean: %f\n pairs: %d".format(t.mean, t.pairs)
    }
    reportService(stats)
  }
}
