package com.github.tekiflo.autocomplete.streams

import java.util.Properties

import com.github.tekiflo.autocomplete.Utils
import org.apache.kafka.streams.kstream.KStreamBuilder
import org.apache.kafka.streams.{KafkaStreams, StreamsConfig}
import org.slf4j.{Logger, LoggerFactory}
import scopt.OptionParser

object KafkaStreamsExample extends StartsWithACKafkaStreams(Utils.SAMPLE_KEYWORDS) with App {
  private lazy val logger: Logger = LoggerFactory.getLogger(getClass)

  /**
    * Parameters for this app.
    */
  protected case class Params(bootstrapServer: String = "", inputTopic: String = "", outputTopic: String = "")

  /**
    * Parses command-line arguments with scopt.
    */
  val parser = new OptionParser[Params]("") {
    head("kafka streams example", "1.0")

    help("help") text "print this usage text"

    opt[String]("bootstrap-server") required() action { (data, conf) =>
      conf.copy(bootstrapServer = data)
    } text "URI of the kafka server. Example: http://IP:9091"

    opt[String]("input-topic") valueName "topic" required() action { (data, conf) =>
      conf.copy(inputTopic = data)
    } text "Input topic where words are sent."

    opt[String]("output-topic") valueName "topic" required() action { (data, conf) =>
      conf.copy(outputTopic = data)
    } text "Output topic."
  }

  /**
    * Parse and start the streaming job.
    */
  parser.parse(args, Params()) match {
    case Some(params) =>
      logger.info(s"Params: $params")
      startStreaming(params)
    case None =>
      logger.error("Error while parsing params.")
  }

  /**
    * Configure and start a streaming job counting words from an input topic.
    *
    * @param params containing input /output topics and kafka's bootstrap server URI.
    */
  def startStreaming(params: Params): Unit = {
    // Kafka Streams configuration.
    val streamingConfig = new Properties
    streamingConfig.put(StreamsConfig.APPLICATION_ID_CONFIG, "example-autocomplete")
    streamingConfig.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, params.bootstrapServer)

    val builder = new KStreamBuilder
    streamingJob(builder, params.inputTopic, params.outputTopic)

    // Start streaming.
    val kafkaStreams = new KafkaStreams(builder, streamingConfig)
    sys.addShutdownHook(kafkaStreams.close())
    kafkaStreams.start()
  }
}
