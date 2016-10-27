
name := """hacker_rank"""

version := "1.0-SNAPSHOT"

mainClass in(Compile, run) := Some("Solution")

lazy val root = project in file(".")

scalaVersion := "2.11.6"


libraryDependencies += "com.google.guava" % "guava" % "18.0"

