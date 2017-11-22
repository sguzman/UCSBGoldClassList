package com.github.sguzman.scala.ucsb.gold.miner

class MainTest extends org.scalatest.FunSuite {
  test("A test should succeed") {
    val user = System.getenv("USER")
    val pass = System.getenv("PASS")

    Main.main(Array("-u", user, "-p", pass, "-o"))
  }
}
