package com.github.sguzman.scala.ucsb.gold.miner.filter

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._

import scalaj.http.HttpResponse

object CourseSplittify {
  def apply(resp: HttpResponse[String]) = {
    val body = resp.body
    val doc = JsoupBrowser().parseString(body)

    val courses = doc >> elementList("#pageContent_CourseList > tbody > tr")
    val courseIds = courses map (_ >> elementList("[id]"))
    val text = courseIds map (_ map (_.text))

    val numCourses = text.map(t => (t.count(_.isEmpty) / 2, t))
    numCourses
  }
}
