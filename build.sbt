lazy val circeVersion        = "0.7.0"

lazy val sharedScalacOptions = Seq(
  "-Xfatal-warnings",
  "-Xfuture",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-language:_",
  "-unchecked")

lazy val wartremoverOptions = List(
  "Any",
  "AsInstanceOf",
  "DefaultArguments",
  "EitherProjectionPartial",
  "Enumeration",
  "Equals",
  "ExplicitImplicitTypes",
  "FinalCaseClass",
  "FinalVal",
  "ImplicitConversion",
  "IsInstanceOf",
  "JavaConversions",
  "LeakingSealed",
  "MutableDataStructures",
  "NoNeedForMonad",
  "NonUnitStatements",
  "Nothing",
  "Null",
  "Option2Iterable",
  "Overloading",
  "Product",
  "Return",
  "Serializable",
  "StringPlusAny",
  "Throw",
  "ToString",
  "TraversableOps",
  "TryPartial",
  "Var",
  "While").map((s: String) => "-P:wartremover:traverser:org.wartremover.warts." + s)

lazy val jvmDependencySettings = Seq.empty

lazy val jsDependencySettings = Seq.empty

lazy val sharedDependencySettings = Seq(
  libraryDependencies ++= Seq(
    compilerPlugin("org.wartremover" %% "wartremover" % "1.2.1"),
    "io.circe"      %%% "circe-core"     % circeVersion,
    "io.circe"      %%% "circe-generic"  % circeVersion,
    "io.circe"      %%% "circe-parser"   % circeVersion,
    "org.scalatest" %%% "scalatest"      % "3.0.1" % "test"))

lazy val sharedSettings = Seq(
  name := "gigue",
  organization := "omoikane.io",
  scalaVersion := "2.12.1",
  scalacOptions := sharedScalacOptions ++ wartremoverOptions) ++ sharedDependencySettings

lazy val gigueJVMSettings = Seq(
  scalacOptions ++= Seq("-Ywarn-dead-code")) ++ jvmDependencySettings

lazy val gigueJSSettings = Seq(
  scalacOptions --= Seq("-Ywarn-dead-code")) ++ jsDependencySettings

lazy val gigue = crossProject.in(file("."))
  .settings(sharedSettings: _*)

lazy val gigueJVM = gigue.jvm
  .settings(gigueJVMSettings: _*)

lazy val gigueJS = gigue.js
  .settings(gigueJSSettings: _*)

lazy val root = (project in file("."))
  .enablePlugins(ScalaJSPlugin)
  .aggregate(gigueJVM, gigueJS)
  .settings(
    publish := {},
    publishLocal := {})
