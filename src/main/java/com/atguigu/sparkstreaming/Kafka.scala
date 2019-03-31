package com.atguigu.sparkstreaming

import kafka.serializer.StringDecoder
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

object Kafka {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("tese")
    val streamingcontexe = new StreamingContext(conf,Seconds(5))
    val confmap = Map(
      ConsumerConfig.GROUP_ID_CONFIG->"atguigu",
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG->"org.apache.kafka.common.serialization.StringDeserializer",
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG->"org.apache.kafka.common.serialization.StringDeserializer",
      "zookeeper.connect"->"hadoop102:2181"
    )
    val topics = Map(
      "sstopic"->2
    )
    val kafkaDStream = KafkaUtils.createStream[String,String,StringDecoder,StringDecoder](streamingcontexe,confmap,topics,StorageLevel.DISK_ONLY)
    kafkaDStream.print()
    streamingcontexe.start()
    streamingcontexe.awaitTermination()
  }

}
