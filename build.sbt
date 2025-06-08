ThisBuild / tlBaseVersion := "0.23" // your current series x.y
ThisBuild / tlMimaPreviousVersions ++= (0 to 11).map(y => s"0.23.$y").toSet

ThisBuild / licenses := Seq(License.Apache2)
ThisBuild / startYear := Some(2014)
ThisBuild / developers := List(
  // your GitHub handle and name
  tlGitHubDev("rossabaker", "Ross A. Baker")
)

// publish website from this branch
ThisBuild / tlSitePublishBranch := Some("main")

val Scala213 = "2.13.16"
ThisBuild / crossScalaVersions := Seq(Scala213, "2.12.20", "3.3.6")
ThisBuild / scalaVersion := Scala213 // the default Scala

val asyncHttpClientVersion = "2.12.3"
val http4sVersion = "0.23.18"
val http4sServletVersion = "0.23.11"
val munitCatsEffectVersion = "2.1.0"
val servletApiVersion = "3.1.0"
val tomcatVersion = "9.0.98"

lazy val root = project
  .in(file("."))
  .enablePlugins(NoPublishPlugin)
  .aggregate(tomcatServer, examples)

lazy val tomcatServer = project
  .in(file("tomcat-server"))
  .settings(
    name := "http4s-tomcat-server",
    description := "Tomcat implementation for http4s servers",
    libraryDependencies ++= Seq(
      "javax.servlet" % "javax.servlet-api" % servletApiVersion % Provided,
      "org.apache.tomcat" % "tomcat-catalina" % tomcatVersion,
      "org.apache.tomcat" % "tomcat-coyote" % tomcatVersion,
      "org.apache.tomcat" % "tomcat-util-scan" % tomcatVersion,
      "org.http4s" %% "http4s-dsl" % http4sVersion % Test,
      "org.http4s" %% "http4s-servlet" % http4sServletVersion,
      "org.typelevel" %% "munit-cats-effect" % munitCatsEffectVersion % Test,
    ),
  )

lazy val examples = project
  .in(file("examples"))
  .enablePlugins(NoPublishPlugin)
  .settings(
    name := "http4s-tomcat-examples",
    description := "Example of http4s server on Tomcat",
    fork := true,
  )
  .dependsOn(tomcatServer)

lazy val docs = project
  .in(file("site"))
  .enablePlugins(Http4sOrgSitePlugin)
