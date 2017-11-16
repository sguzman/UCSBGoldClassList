package com.github.sguzman.scala.ucsb.gold.miner

import java.net.{URI, URLEncoder}
import java.nio.charset.StandardCharsets

import com.beust.jcommander.JCommander
import com.github.sguzman.scala.ucsb.gold.miner.args.Args
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.{HttpGet, HttpPost}
import org.apache.http.client.utils.{URIUtils, URLEncodedUtils}
import org.apache.http.entity.ByteArrayEntity
import org.apache.http.impl.client.{CloseableHttpClient, HttpClientBuilder, HttpClients}
import org.apache.http.util.EntityUtils
import org.jsoup.Jsoup

object Main {
  def main(args: Array[String]): Unit = {
    val argv = new Args
    val j = JCommander.newBuilder()
      .addObject(argv)
      .build()

    j.parse(args: _*)
    if (argv.help) {
      j.usage()
      System.exit(0)
    }

    val client = HttpClientBuilder
      .create
      .build
    val url = "https://my.sa.ucsb.edu/gold/login.aspx"

    val uri = new URI(url)
    val get = new HttpGet(uri)

    get.addHeader("Host", "my.sa.ucsb.edu")
    get.addHeader("Connection", "keep-alive")
    get.addHeader("Upgrade-Insecure-Requests", "1")
    get.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.89 Safari/537.36")
    get.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
    get.addHeader("DNT", "1")
    get.addHeader("Referer", "https://www.google.com/")
    get.addHeader("Accept-Encoding", "gzip, deflate, br")
    get.addHeader("Accept-Language", "en-US,en;q=0.9")

    val resp = client.execute(get)
    val jsoup = Jsoup.parse(EntityUtils.toString(resp.getEntity))

    val valueStr = (List(
      "__LASTFOCUS",
      "__VIEWSTATE",
      "__VIEWSTATEGENERATOR",
      "__EVENTTARGET",
      "__EVENTARGUMENT",
      "__EVENTVALIDATION",
    ).map(s => if (jsoup.selectFirst("#" ++ s) != null) s ++ "=" ++ jsoup.selectFirst("#" ++ s).`val` else "").filter(_.nonEmpty) ++ (if (argv.old)
      List(
        "ctl00$pageContent$userNameText=",
        "ctl00$pageContent$passwordText=",
        "ctl00$pageContent$PermPinLogin$userNameText=" ++ argv.user,
        "ctl00$pageContent$PermPinLogin$passwordText=" ++ argv.pass,
        "ctl00$pageContent$PermPinLogin$loginButton.x=0",
        "ctl00$pageContent$PermPinLogin$loginButton.y=0"
      )
    else
      List(
        "ctl00$pageContent$userNameText=" ++ argv.user,
        "ctl00$pageContent$passwordText=" ++ argv.pass,
        "ctl00$pageContent$PermPinLogin$userNameText=",
        "ctl00$pageContent$PermPinLogin$passwordText=",
        "ctl00$pageContent$loginButton.x=0",
        "ctl00$pageContent$loginButton.y=0"
      ))).mkString("&")


    val a = new URI(url)
    val b = new HttpPost(a)

    b.addHeader("Host", "my.sa.ucsb.edu")
    b.addHeader("Connection", "keep-alive")
    b.addHeader("Cache-Control", "max-age=0")
    b.addHeader("Origin", "https://my.sa.ucsb.edu")
    b.addHeader("Upgrade-Insecure-Requests", "1")
    b.addHeader("Content-Type", "application/x-www-form-urlencoded")
    b.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.89 Safari/537.36")
    b.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
    b.addHeader("DNT", "1")
    b.addHeader("Referer", "https://my.sa.ucsb.edu/gold/login.aspx")
    b.addHeader("Accept-Encoding", "gzip, deflate, br")
    b.addHeader("Accept-Language", "en-US,en;q=0.9")

    val g = valueStr.getBytes(StandardCharsets.UTF_8)
    val h = new ByteArrayEntity(g)
    b.setEntity(h)
    val c = client.execute(b)
    val d = c.getEntity
    val e = EntityUtils.toString(d)
    println(e)
  }
}
