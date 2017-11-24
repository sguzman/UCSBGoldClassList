package com.github.sguzman.scala.ucsb.gold.miner.filter

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Element

import scalaj.http.HttpResponse

object CourseFilter {
  def apply(resp: HttpResponse[String]) = {
    val body = resp.body
    val doc = JsoupBrowser().parseString(body)

    val elements = (doc >> allText("#pageContent_criteriaLabel")) +:
      (doc >> elementList("#pageContent_CourseList > tbody > tr"))
        .zipWithIndex
        .map(course)
      .filter(_.head.nonEmpty)

    elements
  }

  def noEmpty[A](courseList: List[A]): Boolean = {
    courseList.nonEmpty
  }

  def course(tup: (Element, Int)) = {
    val doc = tup._1
    val idx = tup._2
    List(
      doc >> allText(s"#pageContent_CourseList_PermNbr_$idx"),
      doc >> allText(s"#pageContent_CourseList_PrimaryRow_$idx")
    )
  }
}
