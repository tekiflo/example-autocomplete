package com.github.tekiflo.autocomplete.streams

import com.github.tekiflo.autocomplete.StartsWithAutocomplete
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.kstream.KStreamBuilder
import org.slf4j.{Logger, LoggerFactory}

/**
  * Autocomplete with Kafka Streams, using StartsWithAutocomplete.
  *
  * @param keywords list of keywords (could be loaded from a topic as a KTable if it were too big to fit into memory)
  */
class StartsWithACKafkaStreams(keywords: List[String]) {
  private lazy val logger: Logger = LoggerFactory.getLogger(getClass)

  /**
    * Takes a KStreamBuilder to creates streaming transformations needed.
    * With more time or a library, we could easily output a list than a concatenated string for the results.
    *
    * @param builder    input KStreamBuilder.
    * @param inputTopic input topic containing words.
    */
  def streamingJob(builder: KStreamBuilder, inputTopic: String, outputTopic: String): Unit = {
    val stringSerde = Serdes.String
    val stream = builder.stream[String, String](stringSerde, stringSerde, inputTopic)

    val results = stream.map[String, List[String]](
      (_, str: String) => new KeyValue[String, List[String]](str, StartsWithAutocomplete.search(str, keywords))
    )

    results.foreach((str, results) => {
      logger.info(s"search results for $str :")
      results.foreach(s => logger.info("\t" + s))
    })

    results
      .mapValues[String](_ mkString ", ")
      .to(stringSerde, stringSerde, outputTopic)
  }
}
