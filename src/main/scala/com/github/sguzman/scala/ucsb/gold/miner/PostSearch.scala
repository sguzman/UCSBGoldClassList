package com.github.sguzman.scala.ucsb.gold.miner

import java.net.URLEncoder
import java.nio.charset.StandardCharsets.UTF_8

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.elementList

import scalaj.http.{Http, HttpRequest, HttpResponse}

object PostSearch {
  def apply(quarter: String, department: String, response: HttpResponse[String]): HttpRequest = {
    val req = MetaScrape.get(response)

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

    val resp = req.postData(form)
    resp
  }

  def results(httpResponse: HttpResponse[String]): HttpRequest = {
    val url = "https://my.sa.ucsb.edu/gold/ResultsFindCourses.aspx"
    val resp = Http(url).header("Cookie", httpResponse.cookies.mkString("; "))
    resp
  }
}
