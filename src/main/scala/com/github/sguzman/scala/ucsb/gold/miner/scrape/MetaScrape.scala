package com.github.sguzman.scala.ucsb.gold.miner.scrape

import net.ruippeixotog.scalascraper.browser.{Browser, JsoupBrowser}
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._

import scalaj.http.{Http, HttpRequest, HttpResponse}


object MetaScrape {
  def get(req: HttpResponse[String]): HttpRequest = {
    val url = "https://my.sa.ucsb.edu/gold/BasicFindCourses.aspx"
    Http(url)
      .header("Cookie", req.cookies.mkString("; "))
  }

  def apply(req: HttpResponse[String]): (List[String], List[String]) = {
    val url = "https://my.sa.ucsb.edu/gold/BasicFindCourses.aspx"
    val req2 = get(req)
    val doc = JsoupBrowser().parseString(req2.asString.body)

    (quarters(doc), departments(doc).tail)
  }

  private def departments(doc: Browser#DocumentType): List[String] = {
    val id = "pageContent_subjectAreaDropDown"
    val menu = doc >> elementList(s"#$id > option")
    menu.map(_.attr("value"))
  }

  private def quarters(doc: Browser#DocumentType): List[String] = {
    val id = "pageContent_quarterDropDown"
    val menu = doc >> elementList(s"#$id > option")
    menu.map(_.attr("value"))
  }
}