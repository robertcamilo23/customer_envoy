
name := "customer-plan"

version := "1.0"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  "io.grpc"                %  "grpc-netty"           % com.trueaccord.scalapb.compiler.Version.grpcJavaVersion,
  "com.trueaccord.scalapb" %% "scalapb-runtime-grpc" % com.trueaccord.scalapb.compiler.Version.scalapbVersion
)

PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)