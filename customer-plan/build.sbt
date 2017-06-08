
name := "customer-plan"

version := "1.0"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  "io.grpc"                %  "grpc-netty"           % com.trueaccord.scalapb.compiler.Version.grpcJavaVersion,
  "com.trueaccord.scalapb" %% "scalapb-runtime-grpc" % com.trueaccord.scalapb.compiler.Version.scalapbVersion,
  "io.grpc"                % "grpc-okhttp"           % com.trueaccord.scalapb.compiler.Version.grpcJavaVersion
)

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
    from("java:8")
    add(artifact, artifactTargetPath)
    entryPoint("java", "-jar", artifactTargetPath)
  }
}

buildOptions in docker := BuildOptions(cache = false)
