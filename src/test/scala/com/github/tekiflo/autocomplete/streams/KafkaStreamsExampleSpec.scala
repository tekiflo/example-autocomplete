package com.github.tekiflo.autocomplete.streams

import com.madewithtea.mockedstreams.MockedStreams
import com.madewithtea.mockedstreams.MockedStreams.Builder
import org.apache.kafka.common.serialization.{Serde, Serdes}
import org.scalatest._

/**
  * Test cases for ACWithKafkaStreamsExample.
  */
class KafkaStreamsExampleSpec extends FlatSpec with Matchers {
  val stringSerde: Serde[String] = Serdes.String
  val inputTopic: String = "input"
  val outputTopic: String = "output"

  val input: Seq[(String, String)] = Seq(
    "p", "pr", "pro", "prog", "kafka"
  ).map(word => ("", word))

  val mockedStreams: Builder = MockedStreams()
    .topology(builder => KafkaStreamsExample.streamingJob(builder, inputTopic, outputTopic))
    .input(inputTopic, stringSerde, stringSerde, input)

  info("Starting...")

  "Autocomplete with Kafka Streams" should "send lists of filtered keywords starting with the key" in {
    val expected = Seq(
      ("p", "Pandora, Paypal, Pg&e, Phone"),
      ("pr", "Prank, Press democrat, Print, Proactive"),
      ("pro", "Proactive, Processor, Procurable, Progenex"),
      ("prog", "Progenex, Progeria, Progesterone, Programming"),
      ("kafka", "")
    )

    mockedStreams.output(outputTopic, stringSerde, stringSerde, expected.size) shouldEqual expected
  }
}
