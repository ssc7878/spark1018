package com.atguigu.sparkstreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

object testWordCount {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("wordCount")
    val streamingContext = new StreamingContext(conf,Seconds(5))
    val socket = streamingContext.socketTextStream("192.168.1.102",9999)
    val flatmapstream: DStream[String] = socket.flatMap(_.split(" "))
    val mapstream = flatmapstream.map((_,1))
    val reduce = mapstream.reduceByKey(_+_)
    reduce.print()
    streamingContext.start()
    streamingContext.awaitTermination()
  }
}
