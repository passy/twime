package net.rdrei.twime

import java.io.File
import com.github.tototoshi.csv.CSVReader
import scala.util.Try

object CityDatabase {

  private var db = Map.empty[String, String]

  def load(): Unit = {
    val reader = CSVReader.open(new File("data/cities_proper.csv"))
    db = reader.all().map(i => i(0).toLowerCase -> i(1)).toMap
    println(s"CityDatabase initialized with ${db.size} entries.")
  }

  def apply(name: String): Option[String] =
    db.get(name.toLowerCase)

  def find(prefix: String): Option[String] =
    (db find ((entry) => entry._1.startsWith(prefix.toLowerCase))).flatMap((in) => Some(in._2))
}
