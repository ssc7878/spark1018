package com.atguigu.sparkstreaming

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka.KafkaUtils

object kafkaWordCount {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("locak[*]").setAppName("kafka")
    val streamingContext = new StreamingContext(conf, Seconds(5))
    val kafkaparams = Map(
      ConsumerConfig.GROUP_ID_CONFIG -> "",
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> "org.apache.kafka.common.serialization.StringDeserializer",
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> "org.apache.kafka.common.serialization.StringDeserializer",
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> "hadoop102:9092"
    )
    val topic = Map(
      "sstopic" -> 3
    )

    val kafkaconnect = KafkaUtils.createStream(streamingContext, kafkaparams, topic, StorageLevel.DISK_ONLY)

  }
}
