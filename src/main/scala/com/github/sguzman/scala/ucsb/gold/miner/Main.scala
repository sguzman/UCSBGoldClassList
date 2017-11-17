package com.github.sguzman.scala.ucsb.gold.miner

import com.beust.jcommander.JCommander
import com.github.sguzman.scala.ucsb.gold.miner.args.Args
import com.github.sguzman.scala.ucsb.gold.miner.filter.CourseFilter
import com.github.sguzman.scala.ucsb.gold.miner.login.Login
import com.github.sguzman.scala.ucsb.gold.miner.scrape.{CourseScrape, FineScrape}

object Main {
  def main(args: Array[String]): Unit = {
    val argv = new Args
    val j = JCommander.newBuilder()
      .addObject(argv)
      .build()

    j.parse(args: _*)
    if (argv.help) {
      j.usage()
      System.exit(0)
    }

    val jbInit = Login(argv)

    val (quarters, classes, jb) = CourseScrape(jbInit)
    println(quarters, classes)
    val _ = CourseFilter(classes)
    val courseDescr = FineScrape(jb, classes)
    println(courseDescr)

    jb.quit()
  }
}
