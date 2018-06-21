import Dependencies._
import Settings._
import sbt.Keys._

lazy val devModeFlag = "spark-dev-mode"
lazy val sbtModeKey = "sbt"
lazy val isSparkRunningFromSbt =
  Option(System.getProperty(devModeFlag)).contains(sbtModeKey)

lazy val sparkHeapSize = Option(System.getProperty("spark-heap-size"))
  .fold("2g")(identity)

lazy val javaOpts = Seq(
  s"-Xms$sparkHeapSize",
  s"-Xmx$sparkHeapSize"
) ++ (if (isSparkRunningFromSbt) Seq(s"-D$devModeFlag=$sbtModeKey") else Seq.empty)

lazy val sparkDepScope = if (isSparkRunningFromSbt) Compile else Provided

lazy val sparkSettings = Seq(
  connectInput in run := true,
  javaOptions ++= javaOpts,
  javaOptions in IntegrationTest := javaOpts,
  parallelExecution in IntegrationTest := false,
  dependencyOverrides += Misc.jacksonBind,
  libraryDependencies ++= withScope(sparkDepScope)(Spark.bundle) :+ (Spark.kafkaSql % Compile),
  fork in test := true,
  fork in IntegrationTest := true
) ++ Defaults.itSettings

def assemblySettings: List[Def.SettingsDefinition] = List(
  artifact in (Compile, assembly) := {
    val art = (artifact in (Compile, assembly)).value
    art.withClassifier(Some("assembly"))
  },
  addArtifact(artifact in (Compile, assembly), assembly),
  assemblyJarName in assembly := s"${name.value}-${(version in ThisBuild).value}.jar",
  assemblyMergeStrategy in assembly := {
    case PathList("org", "apache", "spark", "unused", "UnusedStubClass.class") => MergeStrategy.first
    case x if x.endsWith(".conf")       => MergeStrategy.discard
    case x if x.endsWith(".xml")        => MergeStrategy.discard
    case x if x.endsWith(".properties") => MergeStrategy.discard
    case x =>
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
  }
)

lazy val commonDependencies = (
  withScope(Test)(Dependencies.specsBundle)
    ++ Dependencies.configBundle
    ++ Dependencies.loggingBundle
    :+ Dependencies.scopt
)

lazy val common = (project in file("common"))
  .settings(name := "common")
  .settings(commonSettings: _*)
  .settings(sparkSettings: _*)
  .settings(libraryDependencies
    ++= commonDependencies
    :+ Misc.enumeratum
  ).disablePlugins(sbtassembly.AssemblyPlugin)

lazy val preprocessor = (project in file("preprocessor"))
  .settings(name := "preprocessor")
  .settings(commonSettings: _*)
  .settings(sparkSettings: _*)
  .settings(assemblySettings: _*)
  .settings(libraryDependencies
    ++= commonDependencies
  ).dependsOn(common)

lazy val assembler = (project in file("assembler"))
  .settings(name := "assembler")
  .settings(commonSettings: _*)
  .settings(sparkSettings: _*)
  .configs(IntegrationTest extend Test)
  .settings(assemblySettings: _*)
  .settings(libraryDependencies
    ++= commonDependencies
    ++ AwsClient.bundle
    :+ Dependencies.scalazCore
    :+ Dependencies.scalaz("scalaz-effect")
    :+ Misc.refinedPureconfig
  ).dependsOn(common)

lazy val demo = (project in file("."))
  .settings(name := "demo")
  .settings(commonSettings: _*)
  .settings(publishArtifact := false)
  .aggregate(common, preprocessor, assembler)
  .disablePlugins(sbtassembly.AssemblyPlugin)
