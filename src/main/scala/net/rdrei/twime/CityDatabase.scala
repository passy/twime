package net.rdrei.twime

import scala.collection.immutable.HashMap
import java.io.File
import com.github.tototoshi.csv.CSVReader

object CityDatabase {

  private var db = Map.empty[String, String]

  def load() : Unit = {
    val reader = CSVReader.open(new File("data/cities_proper.csv"))
    db = reader.all().map(i => i(0).toLowerCase -> i(1)).toMap
    println(s"CityDatabase initialized with ${db.size} entries.")
  }

  def apply(name : String) : Option[String] =
    db.get(name.toLowerCase)

}
