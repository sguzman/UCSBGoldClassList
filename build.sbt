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
  "net.ruippeixotog" %% "scala-scraper" % "2.0.0",
  "org.scalaj" %% "scalaj-http" % "2.3.0",
  "org.slf4j" % "slf4j-api" % "1.7.25",
  "org.slf4j" % "slf4j-jdk14" % "1.7.25",
  "org.feijoas" % "mango_2.12" % "0.14"
)

/** Make sure to fork on run */
fork in run := true