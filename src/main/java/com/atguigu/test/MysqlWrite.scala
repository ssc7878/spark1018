package com.atguigu.test

import java.sql.DriverManager

import org.apache.spark.{SparkConf, SparkContext}

object MysqlWrite {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("write").setMaster("local[*]")
    val sc = new SparkContext(conf)
    /*new JdbcRDD(sc,()=>{

      DriverManager.getConnection()
    })*/
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://hadoop102:3306/test"
    val userName = "root"
    val passWd = "000000"
    val rdd = sc.makeRDD(Array((4,"zhaoliu",20),(5,"tianqi",60)))
    rdd.foreachPartition(datas=>{
      Class.forName(driver)
      val connection = DriverManager.getConnection(url,userName,passWd)
      datas.foreach{
        case(id,name,age)=>{
          val sql = "insert into user values(?,?,?)"
          val statement = connection.prepareStatement(sql)
          statement.setObject(1,id)
          statement.setObject(2,name)
          statement.setObject(3,age)
          statement.executeUpdate()
          statement.close()
        }
      }
      connection.close()
    })
    sc.stop()
  }
}
