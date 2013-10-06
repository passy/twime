name := "twime"

version := "0.1"

scalaVersion := "2.10.2"

libraryDependencies += "com.twitter" % "hbc-core" % "1.4.0" exclude("com.twitter", "joauth")

libraryDependencies += "com.twitter" % "joauth" % "4.0.2-SNAPSHOT"
