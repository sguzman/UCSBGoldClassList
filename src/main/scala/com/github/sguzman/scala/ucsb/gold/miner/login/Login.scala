package com.github.sguzman.scala.ucsb.gold.miner.login

import com.github.sguzman.scala.ucsb.gold.miner.args.Args
import com.machinepublishers.jbrowserdriver.{JBrowserDriver, Settings, Timezone}
import org.openqa.selenium.By

object Login {
  def apply(argv: Args): JBrowserDriver = {
    val (user, pass, submit) = if (argv.old)
      ("pageContent_PermPinLogin_userNameText", "pageContent_PermPinLogin_passwordText", "pageContent_PermPinLogin_loginButton")
    else
      ("pageContent_userNameText", "pageContent_passwordText", "pageContent_loginButton")

    val jb = new JBrowserDriver(Settings.builder.timezone(Timezone.AMERICA_LOSANGELES).build)
    val url = "https://my.sa.ucsb.edu/gold/login.aspx"
    jb.get(url)

    val userText = jb.findElement(By.id(user))
    val passText = jb.findElement(By.id(pass))
    val button = jb.findElement(By.id(submit))

    userText.sendKeys(argv.user)
    passText.sendKeys(argv.pass)
    button.click()

    jb
  }
}
