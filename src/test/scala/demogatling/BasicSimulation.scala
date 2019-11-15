package io

import java.io.{ByteArrayOutputStream, File, FileInputStream, PrintWriter}

import scala.io.Source
import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

class RecordedSimulation extends Simulation {
  val file = Source.fromFile("src/test/scala/demogatling/data/information.txt")
  var host = "https://www.google.com"
  var users = 1;
  var name = "Simulation Test"
  val rows = ArrayBuffer[Array[String]]()
  for (line <- file.getLines) {
    val nameValuePairs: Array[java.lang.String]=line.split("~");
    println(nameValuePairs(0)+"-"+nameValuePairs(1))
    if(nameValuePairs(0)=="Host")
    {
      host=nameValuePairs(1)
    }
    else if(nameValuePairs(0)=="Name")
    {
      name=nameValuePairs(1)
    }
    else if(nameValuePairs(0)=="Users") {
      users = nameValuePairs(1).toInt
    }
  }
  val httpProtocol = http
    .baseURL(host)
    .inferHtmlResources(BlackList(""".*\.js""",""".*\.svg""", """.*\.css""",  """.*\.webp""",""".*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.(t|o)tf""", """.*\.png"""), WhiteList())
    .acceptHeader("*/*")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.9")
    .userAgentHeader("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36")

  val headers_0 = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
    "Upgrade-Insecure-Requests" -> "1")
  val bufferedSource = Source.fromFile("src/test/scala/demogatling/data/urlsConfig.csv")
  var scn = scenario(name)
  for (line <- bufferedSource.getLines) {
    val row = line.split(",").map(_.trim)
    scn = scn.exec(http(row(0).replaceAll("\"", ""))
      .get(row(1).replaceAll("\"", "")).headers(headers_0)).pause(row(2).replaceAll("\"", "").toInt)
  }
  bufferedSource.close

  setUp(scn.inject(rampUsers(users) over (5 seconds))).protocols(httpProtocol)
}