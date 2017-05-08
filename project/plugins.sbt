// The Typesafe repository
resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"

resolvers += Resolver.url("heroku-sbt-plugin-releases", url("https://dl.bintray.com/heroku/sbt-plugins/"))(Resolver.ivyStylePatterns)


// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" %% "sbt-plugin" % "2.5.9")

// To check dependency updates: https://github.com/rtimush/sbt-updates
addSbtPlugin("com.timushev.sbt" %% "sbt-updates" % "0.2.0")

//addSbtPlugin("com.vmunier" % "sbt-web-scalajs" % "1.0.1")

addSbtPlugin("org.ensime" % "ensime-sbt-cmd" % "0.1.2")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.9")

addSbtPlugin("com.vmunier" % "sbt-play-scalajs" % "0.2.8")

addSbtPlugin("com.typesafe.sbt" % "sbt-gzip" % "1.0.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-digest" % "1.1.1")

addSbtPlugin("com.typesafe.sbt" % "sbt-less" % "1.0.6")

addSbtPlugin("com.heroku" % "sbt-heroku" % "0.5.3.1")

