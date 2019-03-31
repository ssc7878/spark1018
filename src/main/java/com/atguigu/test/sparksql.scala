package com.atguigu.test

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object sparksql {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("test")
    val session = SparkSession.builder().config(conf).getOrCreate()
    val frame = session.read.json("input/in/1.json")
    frame.show()
  }
}
