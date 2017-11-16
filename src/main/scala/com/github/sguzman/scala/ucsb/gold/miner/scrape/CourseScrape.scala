package com.github.sguzman.scala.ucsb.gold.miner.scrape

import java.time.{ZoneId, ZonedDateTime}

import com.machinepublishers.jbrowserdriver.JBrowserDriver
import org.openqa.selenium.By

import scala.collection.JavaConverters._

object CourseScrape {
  def apply(jb: JBrowserDriver): (List[Int], List[String], List[String], JBrowserDriver) =
    (years, quarters, departments(jb), jb)

  private def departments(jb: JBrowserDriver) = {
    val url = "https://my.sa.ucsb.edu/gold/BasicFindCourses.aspx"
    jb.get(url)

    depts(jb)
  }

  private def depts(jb: JBrowserDriver) = {
    val body = jb.getPageSource
    val id = "pageContent_subjectAreaDropDown"
    val eles = jb.findElements(By.cssSelector(s"#$id > option"))

    val selectDepts = eles.asScala.map(_.getAttribute("value"))
    val departments = selectDepts.tail.toList

    departments
  }

  private def years =
    List(2010 to ZonedDateTime.now(ZoneId.of("America/Los_Angeles")).getYear: _*)

  private def quarters = List("Fall", "Winter", "Spring", "Summer")
}
