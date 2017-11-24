package com.github.sguzman.scala.ucsb.gold.miner.filter

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._

import scalaj.http.HttpResponse

object CourseFilter {
  def apply(resp: HttpResponse[String]) = {
    val body = resp.body
    val doc = JsoupBrowser().parseString(body)

    val head = doc >> elementList("[id]")
    val text = head.map(_.text)

    text
  }
}
