import ohnosequences.sbt.SbtS3Resolver._
import com.amazonaws.services.s3.model.Region

scalaVersion := "2.11.8"
name := "AppTree Play SDK"
organization := "com.apptreesoftware"

version := "5.5.3-SNAPSHOT"

libraryDependencies ++= Seq(
)

doc in Compile <<= target.map(_ / "none")

lazy val root = (project in file(".")).enablePlugins(PlayJava)
libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  filters
)


libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.8.11.2"
libraryDependencies += "commons-io" % "commons-io" % "2.4"
libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.4"
libraryDependencies += "io.reactivex" % "rxjava" % "1.1.9"
libraryDependencies += "org.jetbrains" % "annotations" % "13.0"


s3region := Region.US_Standard
s3credentials := file(".s3credentials")
publishMavenStyle := true
publishTo := {
  Some(s3resolver.value("Release Bucket", s3("releases.mvn-repo.apptreesoftware.com")) withMavenPatterns)
}
