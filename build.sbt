name := "example-autocomplete"

version := "1.0"

scalaVersion := "2.12.3"

libraryDependencies ++= {
  val kafkaVersion = "0.11.0.1"
  Seq(
    "org.apache.kafka" % "kafka-streams" % kafkaVersion,
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "org.codehaus.groovy" % "groovy" % "2.4.12",
    "com.github.scopt" %% "scopt" % "3.6.0",
    "org.apache.commons" % "commons-lang3" % "3.6",
    "org.scalatest" %% "scalatest" % "3.0.4" % "test",
    "org.apache.kafka" % "kafka-streams" % kafkaVersion % "test" classifier "test",
    "com.madewithtea" %% "mockedstreams" % "1.3.0" % "test" exclude("org.slf4j", "slf4j-log4j12")
  )
}

assemblyMergeStrategy in assembly := {
  x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

assemblyJarName := "example-autocomplete.jar"

mainClass in assembly := Some("com.github.tekiflo.autocomplete.streams.KafkaStreamsExample")