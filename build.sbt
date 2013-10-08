name := "twime"

version := "0.1"

scalaVersion := "2.10.2"

libraryDependencies += "com.twitter" % "hbc-core" % "1.4.0" exclude("com.twitter", "joauth")

libraryDependencies += "com.twitter" % "hbc-twitter4j-v3" % "1.4.0"

libraryDependencies += "com.twitter" % "joauth" % "4.0.2-SNAPSHOT"

libraryDependencies += "org.scalaj" % "scalaj-time_2.10.2" % "0.7"
