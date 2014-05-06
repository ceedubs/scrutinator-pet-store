/* basic project info */
name := "scrutinator-pet-store"

organization := "net.ceedubs"

description := "An implementation of the Swagger Pet Store powered by Scrutinator"

homepage := Some(url("https://github.com/ceedubs/scrutinator-pet-store"))

startYear := Some(2014)

licenses := Seq(
  "MIT License" -> url("http://www.opensource.org/licenses/mit-license.html")
)

scmInfo := Some(
  ScmInfo(
    url("https://github.com/ceedubs/scrutinator-pet-store"),
    "scm:git:https://github.com/ceedubs/scrutinator-pet-store.git",
    Some("scm:git:git@github.com:ceedubs/scrutinator-pet-store.git")
  )
)

/* scala versions and options */
scalaVersion := "2.10.4"

// These options will be used for *all* versions.
scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-encoding", "UTF-8"
)

scalacOptions ++= Seq(
  "-Yclosure-elim",
  "-Yinline"
)

// These language flags will be used only for 2.10.x.
// Uncomment those you need, or if you hate SIP-18, all of them.
scalacOptions <++= scalaVersion map { sv =>
  if (sv startsWith "2.10") List(
    "-Xverify",
    "-Ywarn-all",
    "-feature",
    "-language:postfixOps",
    "-language:implicitConversions",
    "-language:higherKinds"
  )
  else Nil
}

javacOptions ++= Seq("-Xlint:unchecked", "-Xlint:deprecation")

/* dependencies */
libraryDependencies <++= scalaVersion { sv =>
  Seq(
    "net.ceedubs"    %% "scrutinator"             % "0.1.0-SNAPSHOT",
    "net.ceedubs"    %% "scrutinator-scalatra"    % "0.1.0-SNAPSHOT",
    "net.ceedubs"    %% "scrutinator-json4s"      % "0.1.0-SNAPSHOT",
    "net.ceedubs"    %% "scrutinator-swagger"     % "0.1.0-SNAPSHOT",
    "org.scalatra"   %% "scalatra-scalate"        % "2.3.0.RC1")
}

/* you may need these repos */
resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots")
)

/* sbt behavior */
logLevel in compile := Level.Warn

traceLevel := 5

offline := false

/* publishing */
publishMavenStyle := true

publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT")) Some(
    "snapshots" at nexus + "content/repositories/snapshots"
  )
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

mappings in (Compile, packageBin) ~= { (ms: Seq[(File, String)]) =>
  ms filter { case (file, toPath) =>
      toPath != "application.conf"
  }
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <developers>
    <developer>
      <id>ceedubs</id>
      <name>Cody Allen</name>
      <email>ceedubs@gmail.com</email>
    </developer>
  </developers>
)
