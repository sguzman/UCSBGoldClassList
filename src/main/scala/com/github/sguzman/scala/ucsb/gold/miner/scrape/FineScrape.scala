package com.github.sguzman.scala.ucsb.gold.miner.scrape

import com.machinepublishers.jbrowserdriver.JBrowserDriver
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.Select

object FineScrape {
  def apply(jb: JBrowserDriver, cls: List[String]): String = {
    course(jb, cls.head)
  }

  private def course(jb: JBrowserDriver, str: String) = {
    val dropDownID = "pageContent_subjectAreaDropDown"
    val selectElement = jb.findElement(By.id(dropDownID))
    val select = new Select(selectElement)

    select.selectByValue(str)
    val searchID = "pageContent_searchButton"
    val buttonElement = jb.findElement(By.id(searchID))
    buttonElement.click()

    jb.getPageSource
  }
}
