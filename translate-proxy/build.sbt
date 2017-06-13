
name := "translate-proxy"

version := "1.0"

scalaVersion := "2.12.2"

libraryDependencies ++= {
  val circeVersion = "0.7.1"

  Seq(
    "com.typesafe.akka" %% "akka-http"         % "10.0.5",
    "com.typesafe.akka" %% "akka-http-testkit" % "10.0.0",
    "de.heikoseeberger" %% "akka-http-circe"   % "1.14.0",
    "io.circe"          %% "circe-core"        % circeVersion,
    "io.circe"          %% "circe-generic"     % circeVersion,
    "io.circe"          %% "circe-parser"      % circeVersion,
    "org.scalatest"     %  "scalatest_2.12"    % "3.0.1",
    "ch.qos.logback"    % "logback-classic"    % "1.2.3",
    "io.grpc"                %  "grpc-netty"           % com.trueaccord.scalapb.compiler.Version.grpcJavaVersion,
    "com.trueaccord.scalapb" %% "scalapb-runtime-grpc" % com.trueaccord.scalapb.compiler.Version.scalapbVersion
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