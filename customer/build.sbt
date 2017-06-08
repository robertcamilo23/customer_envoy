
name := "customer-service"

version := "1.0"

scalaVersion := "2.12.2"

libraryDependencies ++= {
  val phantomVersion = "2.6.4"
  val circeVersion = "0.7.1"

  Seq(
    "com.typesafe.akka"      %% "akka-http"            % "10.0.5",
    "com.typesafe.akka"      %% "akka-http-testkit"    % "10.0.0",
    "de.heikoseeberger"      %% "akka-http-circe"      % "1.14.0",
    "io.circe"               %% "circe-core"           % circeVersion,
    "io.circe"               %% "circe-generic"        % circeVersion,
    "io.circe"               %% "circe-parser"         % circeVersion,
    "org.scalatest"          %  "scalatest_2.12"       % "3.0.1",
    "ch.qos.logback"         % "logback-classic"       % "1.2.3",
    "com.trueaccord.scalapb" %% "scalapb-runtime-grpc" % com.trueaccord.scalapb.compiler.Version.scalapbVersion,
    "io.grpc"                % "grpc-okhttp"           % com.trueaccord.scalapb.compiler.Version.grpcJavaVersion,
    "io.grpc"                % "grpc-netty"           % com.trueaccord.scalapb.compiler.Version.grpcJavaVersion
  )

}

PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)

assemblyMergeStrategy in assembly := {
  case PathList("javax", "servlet", xs @ _*)         => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
  case "application.conf"                            => MergeStrategy.concat
  case "reference.conf"                              => MergeStrategy.concat
  case "unwanted.txt"                                => MergeStrategy.discard
  case x if x.endsWith("io.netty.versions.properties") => MergeStrategy.first
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

enablePlugins(DockerPlugin)

dockerfile in docker := {
  // The assembly task generates a fat JAR file
  val artifact: File = assembly.value
  val artifactTargetPath = s"/app/${artifact.name}"

  new Dockerfile {
    from("envoy_java:8")
    add(artifact, artifactTargetPath)
    runRaw( "mkdir /var/log/envoy" )
    entryPoint("java", "-jar", artifactTargetPath)
  }
}

buildOptions in docker := BuildOptions(cache = false)
