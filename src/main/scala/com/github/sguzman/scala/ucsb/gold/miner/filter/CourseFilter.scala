package com.github.sguzman.scala.ucsb.gold.miner.filter

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._

import scalaj.http.HttpResponse

object CourseFilter {
  def apply(resp: HttpResponse[String]) = {
    val body = resp.body
    val doc = JsoupBrowser().parseString(body)

    val text1 = doc >> elementList("#pageContent_CourseList > tbody > tr > td > table")

    text1.map(_.text)
  }
}
