name := "prueba"
organization := "com.ceiba"
version := "1.0"

lazy val `prueba` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(ehcache, ws, specs2 % Test, guice, evolutions)

libraryDependencies += "com.typesafe.play" %% "play-slick" % "3.0.0"
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "3.0.0"
libraryDependencies += "com.h2database" % "h2" % "1.4.196"
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.13"

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")