name := "prueba"
organization := "com.ceiba"
version := "1.0"

lazy val `prueba` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

scalaVersion := "2.12.11"

libraryDependencies += "org.typelevel" %% "cats-core" % "2.1.1"
libraryDependencies += "org.typelevel" %% "cats-macros" % "2.1.1"
libraryDependencies += "org.typelevel" %% "cats-kernel" % "2.1.1"

libraryDependencies += "io.monix" %% "monix" % "3.2.1"

libraryDependencies += "com.typesafe.play" %% "play-slick" % "4.0.2"
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "4.0.2"
libraryDependencies += "com.typesafe.play" %% "play-ws" % "2.7.4"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.7.4"
libraryDependencies += "com.typesafe.play" %% "filters-helpers" % "2.7.4"
libraryDependencies += "com.typesafe.play" %% "play-test" % "2.7.4"

libraryDependencies += "org.mockito" % "mockito-core" % "3.3.3" % Test
libraryDependencies += "org.specs2" %% "specs2-mock" % "4.9.4" % Test
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test

libraryDependencies += "io.swagger" %% "swagger-play2" % "1.7.1"
libraryDependencies += "io.swagger" % "swagger-parser" % "1.0.44"
libraryDependencies += "org.webjars" %% "webjars-play" % "2.6.3"
libraryDependencies += "org.webjars" % "swagger-ui" % "2.2.0"

libraryDependencies += "com.h2database" % "h2" % "1.4.200"
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.13"

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")