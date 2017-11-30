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

    val numCourses = text.map(t => (t.count(_.isEmpty) / 2, t)).filter(_._1 != 0).filter(_._2.length > 4)
    val splitByRow = numCourses.map(split)
    splitByRow
  }

  def split(tup: (Int, List[String])): List[String] = {
    val numOfRows = tup._1
    val courseTexts = tup._2
    val rowsInText = courseTexts(4)

    if (numOfRows == 1) {
      List(rowsInText)
    } else {
      val enrollCodePattern = """[1-9][0-9][0-9][0-9][0-9]"""
      val enrollCodes = enrollCodePattern.r.findAllMatchIn(rowsInText).toList
      val splitAtRegex = rowsInText.split(enrollCodePattern).tail
      val addBackEnrollCode = enrollCodes.zip(splitAtRegex).map(t => t._1 + t._2)
      addBackEnrollCode
    }
  }
}
