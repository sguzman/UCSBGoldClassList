/** Name of project */
name := "UCSB Gold Miner"

/** Organization */
organization := "com.github.sguzman"

/** Project Version */
version := "1.0"

/** Scala version */
scalaVersion := "2.12.4"

/** Scalac parameters */
scalacOptions ++= Seq("-feature", "-unchecked", "-deprecation", "-encoding", "utf8")

/** Javac parameters */
javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

/** Resolver */
resolvers ++= Seq(
  "Search Maven" at "https://repo1.maven.org/maven2/",
)

/** Source Dependencies */
libraryDependencies ++= Seq(
  "com.beust" % "jcommander" % "1.72",
  "com.google.code.gson" % "gson" % "2.8.2",
  "org.apache.httpcomponents" % "httpclient" % "4.5.3",
  "com.machinepublishers" % "jbrowserdriver" % "0.17.11",
  "org.jsoup" % "jsoup" % "1.11.1",
  "org.scalatest" %% "scalatest" % "3.2.0-SNAP9" % "test"
)

/** Make sure to fork on run */
fork in run := true