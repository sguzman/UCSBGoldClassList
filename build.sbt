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
  "com.machinepublishers" % "jbrowserdriver" % "0.17.11",
  "org.jsoup" % "jsoup" % "1.11.1",
  "org.scalatest" %% "scalatest" % "3.2.0-SNAP9" % "test",
  "org.seleniumhq.selenium" % "selenium" % "2.0rc2",
  "org.apache.httpcomponents" % "httpclient" % "4.5.3",
  "org.slf4j" % "slf4j-api" % "1.7.25",
  "org.slf4j" % "slf4j-jdk14" % "1.7.25"
)

/** Make sure to fork on run */
fork in run := true