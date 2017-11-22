package com.github.sguzman.scala.ucsb.gold.miner.scrape

import java.net.URLEncoder
import java.nio.charset.StandardCharsets.UTF_8

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.elementList

import scalaj.http.HttpResponse

object FineScrape {
  def apply(quarters: List[String], departments: List[String], resp: HttpResponse[String]) =
    (for (d <- departments) yield d).map(course("20174", _, resp)).toList

  private def course(quarter: String, department: String, request: HttpResponse[String]) = {
    val req = MetaScrape.get(request)

    val soup = JsoupBrowser()
    val doc = soup.parseString(req.asString.body)
    val hidden = doc >> elementList("""input[type="hidden"]""")
    val hiddenVals = hidden.map(s => List(s.attr("name"), s.attr("value")))
    val inputVals = List(
      List("ctl00%24pageContent%24quarterDropDown", quarter),
      List("ctl00%24pageContent%24subjectAreaDropDown", department),
      List("ctl00%24pageContent%24searchButton.x", "0"),
      List("ctl00%24pageContent%24searchButton.y", "0")
    )

    val bodyPairs = hiddenVals ++ inputVals
    val form = bodyPairs.map(s => s"${s.head}=${URLEncoder.encode(s(1), UTF_8.toString)}").mkString("&")

    req.postData(form)
  }
}
