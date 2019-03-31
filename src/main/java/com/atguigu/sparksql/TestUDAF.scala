package com.atguigu.sparksql

import org.apache.spark.SparkConf
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, DoubleType, LongType, StructType}

//弱类型
object TestUDAF {
  def main(args: Array[String]): Unit = {
    val c = new SparkConf().setMaster("local[*]").setAppName("TestUDAF")
    val session = SparkSession.builder().config(c).getOrCreate()
    import session.implicits._
    val avg = new AvgAgeUDAF
    session.udf.register("ageavg",avg)
    val frame = session.read.json("input/in/1.json")
    frame.createOrReplaceTempView("user")
    session.sql("select ageavg(age) from user").show
    session.stop()
  }
}
class AvgAgeUDAF extends UserDefinedAggregateFunction{
  override def inputSchema: StructType = {
    new StructType().add("age",LongType)
  }

  override def bufferSchema: StructType = {
    new StructType().add("sum",LongType).add("age",LongType)
  }

  override def dataType: DataType = DoubleType

  override def deterministic: Boolean = true

  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer(0)=0L
    buffer(1)=0L
  }

  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    buffer(0)=buffer.getLong(0)+input.getLong(0)
    buffer(1)=buffer.getLong(1)+1
  }

  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    buffer1(0)=buffer1.getLong(0)+buffer2.getLong(0)
    buffer1(1)=buffer1.getLong(1)+buffer2.getLong(1)
  }

  override def evaluate(buffer: Row): Any = {
    buffer.getLong(0).toDouble/buffer.getLong(1)
  }
}
