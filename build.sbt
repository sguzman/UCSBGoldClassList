import sbt.Keys._

import scala.concurrent.duration._

/** Name of project */
name := "UCSB Gold Miner"

/** Organization */
organization := "com.github.sguzman"

/** Project Version */
version := "1.0"

/** Scala version */
scalaVersion := "2.12.4"

/** Is this a snapshot version */
isSnapshot := false

/** Work offline wherever possible */
offline := true

/** Scalac parameters */
scalacOptions ++= Seq("-feature", "-unchecked", "-deprecation", "-encoding", "utf8")

/** Javac parameters */
javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

/** Resolver */
resolvers ++= Seq(
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Search Maven" at "https://repo1.maven.org/maven2/",
  "Central Maven" at "http://central.maven.org/maven2/",
  "Sonatype" at "https://oss.sonatype.org/content/repositories/releases/"
)

/** Source Dependencies */
libraryDependencies ++= Seq(
  "org.jsoup" % "jsoup" % "1.11.1",
  "com.beust" % "jcommander" % "1.72",
  "com.google.code.gson" % "gson" % "2.8.2",
  "org.scalatest" % "scalatest_2.12" % "3.2.0-SNAP9" % "test"
)

/** Should tasks be executed in parallel */
parallelExecution := true

/** Poll interval for changed sources */
pollInterval := 500.millis

/** Make sure to fork on run */
fork in run := true