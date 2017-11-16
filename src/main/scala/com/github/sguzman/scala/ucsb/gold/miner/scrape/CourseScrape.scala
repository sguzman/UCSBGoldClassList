package com.github.sguzman.scala.ucsb.gold.miner.scrape

import java.time.{ZoneId, ZonedDateTime}

import com.machinepublishers.jbrowserdriver.JBrowserDriver
import org.jsoup.Jsoup

import scala.collection.JavaConverters._

object CourseScrape {
  def apply(jb: JBrowserDriver): (List[Int], List[String], List[(String, String)], JBrowserDriver) =
    (years, quarters, departments(jb), jb)

  private def departments(jb: JBrowserDriver) = {
    val url = "https://my.sa.ucsb.edu/gold/BasicFindCourses.aspx"
    jb.get(url)

    depts(jb)
  }

  private def depts(jb: JBrowserDriver) = {
    val body = jb.getPageSource
    val jsoup = Jsoup.parse(body)
    val id = "pageContent_subjectAreaDropDown"

    val selectDepts = jsoup.select(s"#$id > option").asScala
    val deptOptions = for (d <- selectDepts) yield s"${d.text}"

    val inner = deptOptions.tail.toList
    val departments = inner.map(_.split(" - "))
    val deptTuples = departments.map(d => (d.head, d.last))

    deptTuples
  }

  private def years =
    List(2010 to ZonedDateTime.now(ZoneId.of("America/Los_Angeles")).getYear: _*)

  private def quarters = List("Fall", "Winter", "Spring", "Summer")
}
