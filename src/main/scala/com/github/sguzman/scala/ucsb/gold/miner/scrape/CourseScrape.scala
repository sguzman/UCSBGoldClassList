package com.github.sguzman.scala.ucsb.gold.miner.scrape

import com.machinepublishers.jbrowserdriver.JBrowserDriver
import org.openqa.selenium.By

import scala.collection.JavaConverters._

object CourseScrape {
  def apply(jb: JBrowserDriver): (List[String], List[String], JBrowserDriver) = {
    val url = "https://my.sa.ucsb.edu/gold/BasicFindCourses.aspx"
    jb.get(url)
    (quarters(jb), departments(jb), jb)
  }

  private def departments(jb: JBrowserDriver) = {
    val id = "pageContent_subjectAreaDropDown"
    val eles = jb.findElements(By.cssSelector(s"#$id > option"))

    val selectDepts = eles.asScala.map(_.getAttribute("value")).toList
    val departments = selectDepts.tail

    departments
  }

  private def quarters(jb: JBrowserDriver) = {
    val id = "pageContent_quarterDropDown"
    val eles = jb.findElements(By.cssSelector(s"#$id > option"))

    val selectQuarts = eles.asScala.map(_.getAttribute("value")).toList
    val quarters = selectQuarts.tail

    quarters
  }
}
