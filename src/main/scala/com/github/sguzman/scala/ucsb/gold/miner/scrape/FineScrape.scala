package com.github.sguzman.scala.ucsb.gold.miner.scrape

import com.github.sguzman.scala.ucsb.gold.miner.args.Args
import com.github.sguzman.scala.ucsb.gold.miner.login.Login
import com.machinepublishers.jbrowserdriver.JBrowserDriver
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.Select

object FineScrape {
  def apply(jb: JBrowserDriver, cls: List[String]): String = {
    cls.map(s => course(jb, s)).mkString
  }

  private def course(jb: JBrowserDriver, str: String) = {
    println(s"Processing $str")

    val dropDownID = "pageContent_subjectAreaDropDown"
    val selectElement = jb.findElement(By.id(dropDownID))
    val select = new Select(selectElement)

    select.selectByValue(str)
    val searchID = "pageContent_searchButton"
    val buttonElement = jb.findElement(By.id(searchID))
    buttonElement.click()

    val url = "https://my.sa.ucsb.edu/gold/BasicFindCourses.aspx"
    val body = jb.getPageSource
    jb.get(url)

    body
  }
}
