name := "sdk"

scalaVersion := "2.11.7"

doc in Compile <<= target.map(_ / "none")

libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.8.11.2"
libraryDependencies += "commons-io" % "commons-io" % "2.4"
libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.4"

javaSource in Compile := baseDirectory.value / "app"
