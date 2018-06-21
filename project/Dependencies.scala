
object Versions {
  lazy val vAsyncHttpClient     = "2.4.1"     // todo - latest is 2.4.4
  lazy val vDisruptor           = "3.3.10"    // todo - latest is 3.4.1
  lazy val vDispatch            = "0.13.3"
  lazy val vDogstatsClient      = "2.3"       // todo - latest is 2.5
  lazy val vGuava               = "20.0"      // todo - latest is 24.0-jre
  lazy val vHdrHistogram        = "1.1.0"
  lazy val vJackson             = "2.8.9"     // todo - latest is 2.9.4
  lazy val vJodaConvert         = "1.9.2"     // todo - latest is 2.0.1
  lazy val vJodaTime            = "2.9.9"
  lazy val vJsr305              = "3.0.2"
  lazy val vLogback             = "1.2.3"
  lazy val vMetricsCore         = "3.2.6"     // todo - latest is 4.0.2
  lazy val vMetricsDatadog      = "1.1.13"
  lazy val vOptParseApplicative = "0.7"
  lazy val vPlay2Scalaz         = "0.5.1"
  lazy val vPlayJson            = "2.6.9"
  lazy val vPureConfig          = "0.9.1"
  lazy val vScalaCheck          = "1.13.5"
  lazy val vScalaLogging        = "3.7.2"
  lazy val vScalaz              = "7.2.20"
  lazy val vScalazSpecs2        = "0.5.2"
  lazy val vScopt               = "3.7.0"
  lazy val vShapeless           = "2.3.3"
  lazy val vSlf4j               = "1.7.25"
  lazy val vSpecs2              = "4.0.2"
  lazy val vTypesafeConfig      = "1.3.3"
}

object Dependencies {
  import sbt._
  import Versions._

  object Spark {
    private def sparkModule(module: String): ModuleID = (
      "org.apache.spark" %% module % "2.3.0"
        exclude("org.slf4j", "slf4j-log4j12")
        exclude("commons-beanutils", "commons-beanutils")
        exclude("com.esotericsoftware.minlog", "minlog")
        exclude("org.apache.hadoop", "hadoop-client")
      )

    private lazy val core = sparkModule("spark-core")
    private lazy val sql = sparkModule("spark-sql")

    lazy val kafkaSql = sparkModule("spark-sql-kafka-0-10")
      .exclude("net.jpountz.lz4", "lz4")

    private lazy val actualHadoop = (
      "org.apache.hadoop" % "hadoop-client" % "2.7.0"
        exclude("org.slf4j", "slf4j-log4j12")
      )

    lazy val bundle = List(core, sql, actualHadoop)
  }
  object AwsClient {
    private lazy val version = "1.11.308"

    def awsModule(module: String): ModuleID =
      "com.amazonaws" % s"aws-java-sdk-$module" % version

    lazy val core = awsModule("core")
    lazy val s3 = awsModule("s3")

    lazy val bundle: Seq[ModuleID] = Seq(core, s3)
  }

  object Misc {
    lazy val jacksonBind = "com.fasterxml.jackson.core" % "jackson-databind" % "2.6.5"
    lazy val enumeratum = "com.beachape" %% "enumeratum" % "1.5.13"
    lazy val refinedPureconfig = "eu.timepit" %% "refined-pureconfig" % "0.8.7"
  }


  lazy val typesafeConfig = "com.typesafe" % "config" % vTypesafeConfig
  lazy val pureConfig = "com.github.pureconfig" %% "pureconfig" % vPureConfig
  lazy val optParseApplicative = "net.bmjames" %% "scala-optparse-applicative" % vOptParseApplicative

  lazy val configBundle = Seq(
    typesafeConfig,
    pureConfig
      .exclude("com.typesafe", "config")
      .exclude("com.chuusai", "shapeless_2.11"),
    shapeless,
    optParseApplicative
      .exclude("org.scalaz", "scalaz-core_2.11"),
    scalazCore
  )

  lazy val scopt = "com.github.scopt" %% "scopt" % vScopt

  def scalaz(module: String): ModuleID =
    scalaGroupModule("org.scalaz", vScalaz)(module)

  lazy val scalazCore = scalaz("scalaz-core")
  lazy val scalazCheckBinding = scalaGroupModule("org.scalaz", s"$vScalaz-scalacheck-1.13")("scalaz-scalacheck-binding")

  lazy val shapeless = "com.chuusai" %% "shapeless" % vShapeless


  lazy val scalaCheck = "org.scalacheck" %% "scalacheck" % vScalaCheck

  def specs2(module: String): ModuleID =
    "org.specs2" %% module % vSpecs2

  lazy val specsCore = specs2("specs2-core")
  lazy val specsScalaz = specs2("specs2-scalaz")
  lazy val specsScalaCheck = specs2("specs2-scalacheck")

  lazy val specsOnlyBundle = List(
    specsCore,
    specsScalaCheck
  )
  lazy val checkOnlyBundle = List(
    scalaCheck,
    scalazCheckBinding
  )
  lazy val specsBundle: List[ModuleID] =
    specsOnlyBundle ++
      checkOnlyBundle

  lazy   val logbackClassic = "ch.qos.logback" % "logback-classic" % vLogback
  val scalaLogging = ("com.typesafe.scala-logging" %% "scala-logging" % vScalaLogging)
    .exclude("org.slf4j", "slf4j-api")

  val slf4jApi = "org.slf4j" % "slf4j-api" % vSlf4j
  val slf4jJcl = "org.slf4j" % "jcl-over-slf4j" % vSlf4j
  val slf4jJul = "org.slf4j" % "jul-to-slf4j" % vSlf4j

  val loggingBundle = List(
    logbackClassic,
    scalaLogging,
    slf4jApi,
    slf4jJcl
  )

  def scalaGroupModule(group: String, version: String)(artifact: String): ModuleID =
    group %% artifact % version

  def withScope(scope: Configuration)(modules: Seq[ModuleID]): Seq[ModuleID] =
    modules.map(_ % scope)
}
