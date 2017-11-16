package com.github.sguzman.scala.ucsb.gold.miner.scrape

import java.net.URI
import java.time.{ZoneId, ZonedDateTime}

import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.jsoup.Jsoup
import org.openqa.selenium.Cookie

object Classes {
  def departments(cookies: Set[Cookie]) = {
    val client = HttpClients.createDefault

    val url = "https://my.sa.ucsb.edu/gold/BasicFindCourses.aspx"
    val uri = new URI(url)
    val get = new HttpGet(uri)

    val cookieStr = cookies.mkString("; ")
    get.addHeader("Cookie", cookieStr)

    val resp = client.execute(get)
    val body = EntityUtils.toString(resp.getEntity)
    body
  }
}
