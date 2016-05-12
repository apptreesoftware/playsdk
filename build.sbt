name := "connector"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  filters
)

libraryDependencies += "commons-dbutils" % "commons-dbutils" % "1.6"
libraryDependencies += "commons-io" % "commons-io" % "2.4"
libraryDependencies += "org.postgresql" % "postgresql" % "9.4-1200-jdbc41"
libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.8.11.2"

fork in run := false

packageName in Universal := "connector"

herokuAppName in Compile := "connector"

lazy val sdk = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean)
