package service

import org.scalatest.FlatSpec

class ReportServiceSpec extends FlatSpec {
  import java.io.File

  private val path = new File(".").getCanonicalFile + "/data/"
  private val stats = TrainingSetStats( path + "input.txt",  path + "targetValid.txt")

  "ReportService" should "a TrainingSetStats object" in {
    ReportService(stats)
  }
}
