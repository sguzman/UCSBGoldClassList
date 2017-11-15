package com.github.sguzman.scala.ucsb.gold.miner

import java.net.URI
import java.nio.charset.StandardCharsets

import com.beust.jcommander.JCommander
import com.github.sguzman.scala.ucsb.gold.miner.args.Args
import org.apache.http.client.methods.{HttpGet, HttpPost}
import org.apache.http.entity.ByteArrayEntity
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils

object Main {
  def main(args: Array[String]): Unit = {
    try {
      val argv = new Args
      val j = JCommander.newBuilder()
        .addObject(argv)
        .build()

      j.parse(args: _*)
      if (argv.help) {
        j.usage()
        System.exit(0)
      }

      val client = HttpClients.createDefault
      val url = "https://my.sa.ucsb.edu/gold/login.aspx"

      val get = getLogin(url)
      val resp = client.execute(get)

      val post = requestPost(url)
      val resp2 = client.execute(post)
      println(EntityUtils.toString(resp2.getEntity))

      val home = "https://my.sa.ucsb.edu/gold/Home.aspx"
      val getAgain = getHome(home)
      val resp3 = client.execute(getAgain)
      println(EntityUtils.toString(resp3.getEntity))
    } catch {
      case e: Throwable => println(e.getMessage)
    }
  }

  private def getLogin(url: String) = {
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

    get
  }

  private def getHome(url: String) = {
    val uri = new URI(url)
    val get = new HttpGet(uri)

    get.addHeader("Host", "my.sa.ucsb.edu")
    get.addHeader("Connection", "keep-alive")
    get.addHeader("Cache-Control", "max-age=0")
    get.addHeader("Upgrade-Insecure-Requests", "1")
    get.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.89 Safari/537.36")
    get.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
    get.addHeader("DNT", "1")
    get.addHeader("Referer", "https://my.sa.ucsb.edu/gold/Login.aspx?ReturnUrl=%2fgold%2fHome.aspx")
    get.addHeader("Accept-Encoding", "gzip, deflate, br")
    get.addHeader("Accept-Language", "en-US,en;q=0.9")

    get
  }

  private def requestPost(url: String) = {
    val uri = new URI(url)
    val post = new HttpPost(uri)

    post.addHeader("Host", "my.sa.ucsb.edu")
    post.addHeader("Connection", "keep-alive")
    post.addHeader("Cache-Control", "max-age=0")

    post.addHeader("Origin", "https://my.sa.ucsb.edu")
    post.addHeader("Upgrade-Insecure-Requests", "1")
    post.addHeader("Content-Type", "application/x-www-form-urlencoded")

    post.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.89 Safari/537.36")
    post.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
    post.addHeader("DNT", "1")

    post.addHeader("Referer", "https://my.sa.ucsb.edu/gold/login.aspx")
    post.addHeader("Accept-Encoding", "gzip, deflate, br")
    post.addHeader("Accept-Language", "en-US,en;q=0.9")

    val body = """__LASTFOCUS=&__VIEWSTATE=%2FwEPDwUKMTMyNDgwNDc4MA9kFgJmD2QWAgIHD2QWAgIDD2QWDgIBD2QWAmYPFgIeBFRleHQFWjxsaW5rIGhyZWY9Ii9nb2xkL2Nzcz92PWpGaUFKR0NUdjNkNnBucFNEVE9PcEJjWFpWbUZiQTRrSnN4dVVVWm0tR1UxIiByZWw9InN0eWxlc2hlZXQiLz4NCmQCAg8PFgIeB1Zpc2libGVoZGQCAw8PZBYCHgxhdXRvY29tcGxldGUFA29mZmQCBA8PZBYCHwIFA29mZmQCBg9kFgJmD2QWBAIBDw8WAh8BaGRkAgcPFgIfAWhkAgcPDxYCHgtOYXZpZ2F0ZVVybAUtLy9teS5zYS51Y3NiLmVkdS9QZXJtUGluUmVzZXQvRm9yZ290UGVybS5hc3B4ZGQCCA8PFgIfAwUvLy9teS5zYS51Y3NiLmVkdS9QZXJtUGluUmVzZXQvUGVybVBpblJlc2V0LmFzcHhkZBgBBR5fX0NvbnRyb2xzUmVxdWlyZVBvc3RCYWNrS2V5X18WAgUdY3RsMDAkcGFnZUNvbnRlbnQkbG9naW5CdXR0b24FKmN0bDAwJHBhZ2VDb250ZW50JFBlcm1QaW5Mb2dpbiRsb2dpbkJ1dHRvbg7gU2LMY%2B7z5YeJmU0aRdSXzbK%2BBOudBxptmKI9uxfJ&__VIEWSTATEGENERATOR=00732C32&__EVENTTARGET=&__EVENTARGUMENT=&__EVENTVALIDATION=%2FwEdAAfqon3EJIuTakDQrrS%2FglMdFPojxflIGl2QR%2F%2B%2F4M%2BLrK6wLDfR%2B5jffPpLqn7oL3ttZruIm%2FYRHYjEOQyILgzL2Nu6XIik3f0iXq7Wqnb39%2FZNiE%2FA9ySfq7gBhQx160NmmrEFpfb3YUvL%2Bk7EbVnKKH4aETxjSpLvV0cWRQ4QAysVDTlRUV3ElSKnKpwiWsY%3D&ctl00%24pageContent%24userNameText=&ctl00%24pageContent%24passwordText=&ctl00%24pageContent%24PermPinLogin%24userNameText=5300884&ctl00%24pageContent%24PermPinLogin%24passwordText=8792&ctl00%24pageContent%24PermPinLogin%24loginButton.x=117&ctl00%24pageContent%24PermPinLogin%24loginButton.y=8"""
    val entity = new ByteArrayEntity(body.getBytes(StandardCharsets.UTF_8))
    post.setEntity(entity)

    post
  }
}
