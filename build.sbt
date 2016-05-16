name := "sdk"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc
)

doc in Compile <<= target.map(_ / "none")