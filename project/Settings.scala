import sbt.Def
object Settings {
  import sbt._
  import sbt.Keys._

  private val env = Option(System.getenv("CI_ENV"))
  private val isCiEnv = env.fold(false)(_ == "ci")

  val testSettings = Seq(
    parallelExecution in IntegrationTest := !isCiEnv,
    testForkedParallel in IntegrationTest := !isCiEnv,
    scalacOptions in Test ++= Seq("-Yrangepos"),
    javaOptions ++= Seq("-Xmx2G", "-Dfile.encoding=UTF8"),
    javaOptions in Test ++= Seq("-Xmx2G", "-Dfile.encoding=UTF8")
  )

  val defaultSettings = Seq(
    organization := "com.example",
    scalaVersion := "2.11.12"
  )

  val commonSettings: Seq[Def.Setting[_]] =
    defaultSettings ++ testSettings
}
