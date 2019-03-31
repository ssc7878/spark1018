package com.atguigu.test

import org.apache.spark.{SparkConf, SparkContext}

object Test {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("test")
    val sc = new SparkContext(conf)

    val fileRDD = sc.textFile("input/in/agent.log")
    //1516609143867 6 7 64 16
    // （(省份+广告)，次数）
    val rdd1 = fileRDD.map {
      x =>
        val list = x.split(" ")
        ((list(1), list(4)), 1)
    }
    val rdd2 = rdd1.reduceByKey(_ + _)

    //（省份，（广告，次数））
    val rdd3 = rdd2.map {
      case ((sheng, guanggao), num) => {
        (sheng, (guanggao, num))
      }
    }
    val rdd4 = rdd3.groupByKey()
    val rdd5 = rdd4.mapValues { x =>
      x.toList.sortWith((x, y) => x._2 > y._2).take(3)
    }

    rdd5.collect.foreach(println)
  }
}
