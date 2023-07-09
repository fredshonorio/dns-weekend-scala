val Scala213 = "2.13.10"

lazy val main = project
  .in(file("."))
  .settings(
    name := "dns-weekend-scala",
    libraryDependencies ++= Seq(
      "org.scodec" %% "scodec-core" % "1.11.10",
      "co.fs2" %% "fs2-scodec" % "3.7.0",
      "org.typelevel" %% "cats-core" % "2.9.0",
      "org.typelevel" %% "cats-effect" % "3.5.1",
      "org.scalameta" %% "munit" % "0.7.29" % Test,
      "org.typelevel" %% "munit-cats-effect-3" % "1.0.7" % Test
    )
  )

Global / onChangedBuildSource := ReloadOnSourceChanges
