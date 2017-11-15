import sbt.Keys._

import scala.concurrent.duration._

/** Name of project */
name in Scope.Global := "UCSB Gold Miner"

/** Organization */
organization in Scope.Global := "com.github.sguzman"

/** Project Version */
version in Scope.Global := "1.0"

/** Scala version */
scalaVersion in Scope.Global := "2.12.4"

/** Is this a snapshot version */
isSnapshot in Scope.Global := false

/** Work offline wherever possible */
offline in Scope.Global := true

/** Scalac parameters */
scalacOptions in Scope.Global ++= Seq("-feature", "-unchecked", "-deprecation", "-encoding", "utf8")

/** Javac parameters */
javacOptions in Scope.Global ++= Seq("-source", "1.8", "-target", "1.8")

/** Resolver */
resolvers in Scope.Global ++= Seq(
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Search Maven" at "https://repo1.maven.org/maven2/"
)

/** Source Dependencies */
libraryDependencies in Scope.Global ++= Seq(
  "net.ruippeixotog" % "scala-scraper_2.12" % "2.0.0",
  "com.beust" % "jcommander" % "1.72",
  "com.google.code.gson" % "gson" % "2.8.2",
  "org.scalatest" % "scalatest_2.12" % "3.2.0-SNAP9" % "test"
)

/** Should tasks be executed in parallel */
parallelExecution in Scope.Global := true

/** Poll interval for changed sources */
pollInterval in Scope.Global := 500.millis

/** Make sure to fork on run */
fork in run := true