package MapReduceUseCases

// compare if two log events are same

import org.apache.spark.{SparkConf, SparkContext}

case class Events(IP: String, timeStamp: String, request: String)

object ApacheLogCompare {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("ApacheLogCompare")
    val sc = new SparkContext(conf)
    val logInput = sc.textFile("/path/to/logFile")
    val event = logInput.map(line => parse(line))
    val distinctEvents = event.distinct()
    distinctEvents.saveAsTextFile("/path/to/textFile")
  }
  def parse(logLines: String): Events = {
    val parts = logLines.split(" ") // try building it with regex, it will work here as everything is String type
    val IP = parts(0)
    val timeStamp = parts(3)
    val request = parts(9)
    Events(IP, timeStamp, request)
  }
}

