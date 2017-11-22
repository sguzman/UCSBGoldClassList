package com.github.sguzman.scala.ucsb.gold.miner

import com.beust.jcommander.JCommander
import com.github.sguzman.scala.ucsb.gold.miner.args.Args
import com.github.sguzman.scala.ucsb.gold.miner.filter.CourseFilter
import com.github.sguzman.scala.ucsb.gold.miner.login.Login
import com.github.sguzman.scala.ucsb.gold.miner.scrape.{MetaScrape, PostSearch}

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

    val resp = Login.getUntilSome(argv)
    val (quarters, departments) = MetaScrape(resp)
    val logins = departments.par.map(s => Login.getUntilSome(argv)).toList
    val courses = logins.indices.par.map(i => PostSearch("20174", departments(i), logins(i))).map(_.asString).toList
    val results = logins.par.map(PostSearch.results).map(_.asString)
    val text = results.par.map(CourseFilter.apply)
    text foreach println
  }
}
