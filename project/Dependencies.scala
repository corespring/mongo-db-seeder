import sbt._

object Dependencies {

  object Resolvers {
    val typesafe = "typesafe.com" at "http://repo.typesafe.com/typesafe/releases/"
    val sonatype = "sonatype" at "http://oss.sonatype.org/content/repositories/releases"
    val sonatypeS = "sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots"
    val sbtPlugins = "sbt plugins" at "http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases/"
    val commons = Seq(sonatypeS, sonatype, typesafe)
  }

    val casbah = "org.mongodb" %% "casbah" % "2.6.3"
    val specs2 = "org.specs2" %% "specs2" % "2.3.6" % "test"
}