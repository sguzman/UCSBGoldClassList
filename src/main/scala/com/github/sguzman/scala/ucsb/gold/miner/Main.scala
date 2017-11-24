package com.github.sguzman.scala.ucsb.gold.miner

import com.github.sguzman.scala.ucsb.gold.miner.args.Args
import com.github.sguzman.scala.ucsb.gold.miner.filter.CourseFilter
import com.github.sguzman.scala.ucsb.gold.miner.login.Login
import com.github.sguzman.scala.ucsb.gold.miner.scrape.{MetaScrape, PostSearch}

object Main {
  def main(args: Array[String]): Unit = {
    val argv = Args(args)
    Main(argv)
  }

  def apply(argv: Args): Unit = {
    apply(argv.user, argv.pass, argv.old)
  }

  def apply(user: String, pass: String, old: Boolean): Unit = {
    val argv = new Args
    argv.user = user
    argv.pass = pass
    argv.old = old

    val resp = Login.getUntilSome(argv)
    val (quarters, departments) = MetaScrape(resp)
    val arguments = departments.map(("20174", _))

    val logins = arguments.par.map(_ => Login.getUntilSome(argv)).toList
    val arguments2 = arguments.zip(logins).map(t => (t._1._1, t._1._2, t._2))

    val courses = arguments2.par.map(i => PostSearch(i._1, i._2, i._3)).map(_.asString).toList

    val results = logins.par.map(PostSearch.results).map(_.asString)
    val text = results.par.map(CourseFilter.apply)

    text foreach println
  }
}
