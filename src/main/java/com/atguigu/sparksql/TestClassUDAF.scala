package com.atguigu.sparksql

import org.apache.spark.SparkConf
import org.apache.spark.sql.{Encoder, Encoders, SparkSession}
import org.apache.spark.sql.expressions.Aggregator


//强类型
object TestClassUDAF {
  def main(args: Array[String]): Unit = {
    val c = new SparkConf().setMaster("local[*]").setAppName("TestUDAF")
    val session = SparkSession.builder().config(c).getOrCreate()
    import session.implicits._
    val frame = session.read.json("input/in/1.json")
    val dataset = frame.as[Emp]
    val avg = new AvgAgeClassUDAF
    val empavg = avg.toColumn.name("empavg")
    dataset.select(empavg).show

  }
}
class AvgAgeClassUDAF extends Aggregator[Emp ,AgeBuffer ,Double ]{
  override def zero: AgeBuffer = {
    AgeBuffer(0L,0L)
  }

  override def reduce(b: AgeBuffer, a: Emp): AgeBuffer ={
    b.sum = b.sum+a.age
    b.count=b.count+1
    b
  }

  override def merge(b1: AgeBuffer, b2: AgeBuffer): AgeBuffer ={
    b1.sum+=b2.sum
    b1.count+=b2.count
    b1
  }

  override def finish(reduction: AgeBuffer): Double ={
    reduction.sum.toDouble/reduction.count
  }

  override def bufferEncoder: Encoder[AgeBuffer] =Encoders.product
  override def outputEncoder: Encoder[Double] =Encoders.scalaDouble
}

case class Emp(name:String,age:Long)
case class AgeBuffer(var sum:Long,var count:Long)
