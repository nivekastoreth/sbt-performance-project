object Settings {
  import sbt._
  import sbt.Keys._
  import com.lucidchart.sbt.scalafmt.ScalafmtCorePlugin.autoImport._

  private val env = Option(System.getenv("CI_ENV"))
  private val isCiEnv = env.fold(false)(_ == "ci")

  val testSettings = Seq(
    parallelExecution in IntegrationTest := !isCiEnv,
    testForkedParallel in IntegrationTest := !isCiEnv,
    scalacOptions in Test ++= Seq("-Yrangepos"),
    javaOptions ++= Seq("-Xmx2G", "-Dfile.encoding=UTF8"),
    javaOptions in Test ++= Seq("-Xmx2G", "-Dfile.encoding=UTF8")
  )

  val scalafmtSettings = Seq(
    scalafmtVersion := "1.5.1",
    scalafmtOnCompile in Compile := true
  )
  
  val defaultSettings = Seq(
    organization := "com.example",
    scalaVersion := "2.11.12",
  )

  val commonSettings = defaultSettings ++ testSettings ++ scalafmtSettings
}
