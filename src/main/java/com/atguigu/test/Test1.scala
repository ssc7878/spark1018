package com.atguigu.test

object Test1 {
  def main(args: Array[String]): Unit = {
    val tuples = Array(("1","a"),("2","b"))
    tuples.map(n=>(println(n._1,n._2)))
  }
}
