ThisBuild / tlBaseVersion := "0.25" // your current series x.y

ThisBuild / licenses := Seq(License.Apache2)
ThisBuild / developers := List(
  // your GitHub handle and name
  tlGitHubDev("rossabaker", "Ross A. Baker")
)

// publish website from this branch
ThisBuild / tlSitePublishBranch := Some("main")

val Scala213 = "2.13.8"
ThisBuild / crossScalaVersions := Seq(Scala213, "2.12.15", "3.2.0")
ThisBuild / scalaVersion := Scala213 // the default Scala

val asyncHttpClientVersion = "2.12.3"
val http4sVersion = "0.23.12"
val http4sServletVersion = "0.25.0-M1"
val munitCatsEffectVersion = "1.0.7"
val servletApiVersion = "5.0.0"
val tomcatVersion = "10.0.22"

lazy val root = project
  .in(file("."))
  .enablePlugins(NoPublishPlugin)
  .aggregate(tomcatServer, examples)

lazy val tomcatServer = project
  .in(file("tomcat-server"))
  .settings(
    name := "http4s-tomcat-server",
    description := "Tomcat implementation for http4s servers",
    startYear := Some(2014),
    libraryDependencies ++= Seq(
      "jakarta.servlet" % "jakarta.servlet-api" % servletApiVersion % Provided,
      "org.apache.tomcat" % "tomcat-catalina" % tomcatVersion,
      "org.apache.tomcat" % "tomcat-coyote" % tomcatVersion,
      "org.apache.tomcat" % "tomcat-util-scan" % tomcatVersion,
      "org.http4s" %% "http4s-dsl" % http4sVersion % Test,
      "org.http4s" %% "http4s-servlet" % http4sServletVersion,
      "org.typelevel" %% "munit-cats-effect-3" % munitCatsEffectVersion % Test,
    ),
  )

lazy val examples = project
  .in(file("examples"))
  .enablePlugins(NoPublishPlugin)
  .settings(
    name := "http4s-tomcat-examples",
    description := "Example of http4s server on Tomcat",
    startYear := Some(2014),
    fork := true,
  )
  .dependsOn(tomcatServer)

lazy val docs = project.in(file("site")).enablePlugins(TypelevelSitePlugin)
