package com.atguigu.test

import java.sql.DriverManager

import org.apache.spark.rdd.JdbcRDD
import org.apache.spark.{SparkConf, SparkContext}

object MysqlRDD {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("test")
    val sc = new SparkContext(conf)


    val unit = new JdbcRDD(sc,
      () => {
        val driver = "com.mysql.jdbc.Driver"
        val url = "jdbc:mysql://hadoop102:3306/test"
        val userName = "root"
        val passWd = "000000"
        Class.forName(driver)
        DriverManager.getConnection(url, userName, passWd)
      },
      "select * from user where id >=? and id <=?",
      1, 2, 1,
      r => (r.getInt(1), r.getString(2), r.getInt(3))
    )
    unit .foreach{
      case(id,name,age)=>{
        println(id+","+name+","+age)
      }
    }
    sc.stop()
  }
}
