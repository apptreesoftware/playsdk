name := "sdk"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  javaJdbc,
  javaWs,
  cache
)

doc in Compile <<= target.map(_ / "none")

libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.8.11.2"
libraryDependencies += "commons-io" % "commons-io" % "2.4"
libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.4"
libraryDependencies += "io.reactivex" % "rxjava" % "1.1.9"
libraryDependencies += "commons-net" % "commons-net" % "3.6"
libraryDependencies += "net.coobird" % "thumbnailator" % "0.4.8"
libraryDependencies += "org.avaje.ebeanorm" % "avaje-ebeanorm-mocker" % "8.1.1"
libraryDependencies += "org.mockito" % "mockito-all" % "1.9.5" % "test"

