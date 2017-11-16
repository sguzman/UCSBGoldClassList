package com.github.sguzman.scala.ucsb.gold.miner.scrape

import java.net.URI
import java.time.{ZoneId, ZonedDateTime}

import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.jsoup.Jsoup
import org.openqa.selenium.Cookie

import scala.collection.JavaConverters._

object CourseScrape {
  def apply(cookie: Set[Cookie]) = {
    (years, quarters, departments(cookie))
  }

  private def departments(cookies: Set[Cookie]) = {
    val client = HttpClients.createDefault

    val url = "https://my.sa.ucsb.edu/gold/BasicFindCourses.aspx"
    val uri = new URI(url)
    val get = new HttpGet(uri)

    val cookieStr = cookies.mkString("; ")
    get.addHeader("Cookie", cookieStr)

    val resp = client.execute(get)
    val body = EntityUtils.toString(resp.getEntity)
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
