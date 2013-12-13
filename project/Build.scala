import sbt._
import Keys._

object Build extends sbt.Build {

  import Dependencies._

  val name = "mongo-db-seeder"

  val baseVersion = "0.7"

  lazy val appVersion = {
    val other = Process("git rev-parse --short HEAD").lines.head
    baseVersion + "-" + other
  }

  def buildSettings = Defaults.defaultSettings ++ Seq(
    organization := "org.corespring",
    scalaVersion := "2.10.3",
    crossScalaVersions := Seq("2.10.0"),
    version := appVersion,
    resolvers ++= Resolvers.commons,
    parallelExecution in Test := false,
    credentials += Credentials(Path.userHome / ".ivy2" / ".credentials"),
    publishTo <<= version {
      (v: String) =>
        def isSnapshot = v.trim.contains("-")
        val base = "http://repository.corespring.org/artifactory"
        val repoType = if (isSnapshot) "snapshot" else "release"
        val finalPath = base + "/ivy-" + repoType + "s"
        Some( "Artifactory Realm" at finalPath )
    },
    scalacOptions := Seq(
      "-deprecation",
      "-unchecked")
  )


  val lib = Project(name + "-lib", file("modules/lib"), settings = buildSettings)
    .settings(libraryDependencies ++= Seq(casbah, specs2))

  val plugin = Project(name + "-sbt", file("modules/sbt"), settings = buildSettings)
    .dependsOn(lib)
    .settings(sbtPlugin := true)

  val main = Project(name, base = file("."), settings = buildSettings )
    .dependsOn(lib, plugin)
    .aggregate(lib, plugin)
}