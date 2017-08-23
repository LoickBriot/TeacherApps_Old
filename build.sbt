lazy val scalaV = "2.11.8"
name := "teacherapps"


import sbt._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt.Keys._
import sbt.Project.projectToRef

//common settings for the project and subprojects
lazy val commonSettings = Seq(
  organization := "eu.lbriot",
  version := "0.1.0",
  scalaVersion := scalaV,
  scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-target:jvm-1.8")
)



lazy val slick = (project in file("webapp/slick"))
  .settings(name := "teacherapps_slick")
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2",
      "org.slf4j" % "slf4j-api" % "1.7.23",
      "org.slf4j" % "log4j-over-slf4j" % "1.7.23",  // for any java classes looking for this
      "ch.qos.logback" % "logback-classic" % "1.2.1",
      "com.typesafe.slick" %% "slick" % "3.1.1",
      "com.typesafe.slick" %% "slick-codegen" % "3.1.1",
      "com.github.tminglei" %% "slick-pg" % "0.14.4",
      "com.github.tminglei" %% "slick-pg_date2" % "0.14.4",
      "org.postgresql" % "postgresql" % "9.4.1212",
      "com.cronutils" % "cron-utils" % "5.0.5"
    )
  )


/*  PART OF THE CODE SHARED BETWEEN THE SERVER AND CLIENT PARTS */
lazy val shared = (crossProject.crossType(CrossType.Pure) in file("webapp/shared"))
  .settings(name := "teacherapps_shared")
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "com.lihaoyi" %%% "utest" % "0.3.1",
      "com.lihaoyi" %%% "upickle" % "0.4.3",
      "com.lihaoyi" %%% "autowire" % "0.2.5",
      "com.lihaoyi" %%% "scalarx" % "0.3.1",
      "me.chrons" %%% "boopickle" % "1.1.3",
      "org.scala-lang.modules" %% "scala-xml" % "1.0.1"
    )
  )
  .jsConfigure(_ enablePlugins ScalaJSPlay)

lazy val sharedJVM = shared.jvm.settings(name := "sharedJVM")
lazy val sharedJS = shared.js.settings(name := "sharedJS")




/* CLIENT PART */
lazy val client = (project in file("webapp/client"))
  .settings(name := "teacherapps_client")
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.9.0",
      "be.doeraene" %%% "scalajs-jquery" % "0.9.0",
      "com.lihaoyi" %%% "scalatags" % "0.5.2",
      "com.lihaoyi" %%% "upickle" % "0.4.3",
      "com.lihaoyi" %%% "scalarx" % "0.2.8",
      "org.singlespaced" %%% "scalajs-d3" % "0.3.3",
      "com.github.japgolly.scalajs-react" %%% "core" % "0.11.2",
      "fr.hmil" %%% "roshttp" % "1.1.0",
      "org.scala-lang.modules" % "scala-xml_2.11" % "1.0.6",
      "org.singlespaced" %%% "scalajs-d3" % "0.3.3"
    )
  )
  .settings(
    jsDependencies ++= Seq(
      RuntimeDOM % "test",
      "org.webjars.bower" % "react" % "15.3.2"
        /        "react-with-addons.js"
        minified "react-with-addons.min.js"
        commonJSName "React",

      "org.webjars.bower" % "react" % "15.3.2"
        /         "react-dom.js"
        minified  "react-dom.min.js"
        dependsOn "react-with-addons.js"
        commonJSName "ReactDOM",

      "org.webjars.bower" % "react" % "15.3.2"
        /         "react-dom-server.js"
        minified  "react-dom-server.min.js"
        dependsOn "react-dom.js"
        commonJSName "ReactDOMServer")
  )
  .settings(skip in packageJSDependencies := false)
  .settings(JsEngineKeys.engineType := JsEngineKeys.EngineType.Node)
  .settings(scalaJSUseRhino in Global := false)
  .settings(crossTarget in(Compile, fastOptJS) := new java.io.File("webapp/server/public/assets/js"))
  .settings(crossTarget in(Compile, packageScalaJSLauncher) := new java.io.File("webapp/server/public/assets/js"))
  .settings(crossTarget in(Compile, packageJSDependencies) := new java.io.File("webapp/server/public/assets/js"))
  .settings(crossTarget in(Compile, fullOptJS) := new java.io.File("webapp/server/public/assets/js"))
  .enablePlugins(ScalaJSPlugin, ScalaJSPlay)
  .dependsOn(sharedJS)




/* SERVER PART */
lazy val server  = (project in file("webapp/server"))
  .settings(name := "teacherapps_server")
  .settings(commonSettings: _*)
  .settings(routesGenerator := InjectedRoutesGenerator)
  .settings(TwirlKeys.templateImports += "teacherapps.dbmodels.Tables._")
  .settings(TwirlKeys.templateImports += "play.api.Play.current")
  .settings(TwirlKeys.templateImports += "play.api.i18n.Messages.Implicits._")
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "org.webjars" %% "webjars-play" % "2.5.0",
      play.sbt.Play.autoImport.cache,
      "com.github.ancane" %% "hashids-scala" % "1.2",
      "com.vmunier" %% "play-scalajs-scripts" % "0.4.0",
      "org.scala-lang.modules" %% "scala-xml" % "1.0.1",
      "jp.t2v" %% "play2-auth" % "0.14.2",
      play.sbt.Play.autoImport.cache,
      "com.typesafe.play" %% "play-slick" % "2.0.2",
      "com.github.t3hnar" %% "scala-bcrypt" % "3.0",
      "com.lihaoyi" %% "upickle" % "0.4.3",
      "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2",
      "org.slf4j" % "slf4j-api" % "1.7.23",
      "org.slf4j" % "log4j-over-slf4j" % "1.7.23",  // for any java classes looking for this
      "ch.qos.logback" % "logback-classic" % "1.2.1",
      "com.typesafe.slick" %% "slick" % "3.1.1",
      "com.typesafe.slick" %% "slick-codegen" % "3.1.1",
      "com.github.tminglei" %% "slick-pg" % "0.14.4",
      "com.github.tminglei" %% "slick-pg_date2" % "0.14.4",
      "org.postgresql" % "postgresql" % "9.4.1212",
      "com.cronutils" % "cron-utils" % "5.0.5",
      "org.apache.pdfbox" % "pdfbox" % "2.0.5",
      "org.apache.poi" % "poi" % "3.15",
      "org.apache.poi" % "poi-ooxml" % "3.15",
      "org.apache.commons" % "commons-lang3" % "3.1"
    )
  )
  .settings(scalaJSProjects := Seq(client))
  .settings(pipelineStages := Seq(scalaJSProd, gzip))
  .settings(  resolvers ++= Seq(
    "repo.codahale.com" at "http://repo.codahale.com",
    "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
    "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
    "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/",
    "Typesafe Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/"
  )
  )
  .enablePlugins(PlayScala)
  .aggregate(Seq(client).map(projectToRef): _*)
  .dependsOn(sharedJVM)
  .dependsOn(slick)



