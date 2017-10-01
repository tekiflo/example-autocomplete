# Sample code for a simple autocomplete
Sample code to autocomplete a word search from a list of words.
Written in limited time (approx. 3 hours).

## How to use it ?
Run the tests with `sbt test`. If you have a working Kafka, you can also create a
JAR archive with `sbt assembly` to create the kafka streams job, and then launch it
using `java -jar JARNAME -bootstrap-server SERVER -input-topic INPUT -output-topic OUTPUT`.

## Available features
- Offer up to 4 suggested “auto­complete” based on the letters typed (not case sensitive) ; 
- Matches the start of the keywords (with `StartsWithAutocomplete`) or any portion
of the keywords (with `ContainsAutocomplete`) ;
- Uses *Apache Common Lang* for efficients and case insensitive `startsWith` / `contains` ; 
- Sample distributed streaming processing in *Kafka Streams*
- Tests provided (*Mocked Streams* is used to test the *Kafka Streams* job).

## Possible improvements

### Keyword search
Keyword filtering is currently naive, it could be more efficient to precompute a
graph for these keywords, optimizing the memory footprint and computation speed.
But you could not match any portion of the string.

### A bigger list of keywords
If the list of keywords is too big to fit in RAM, by editing the sample
Kafka Streams job to read this list from another topic, and then joining this stream with
the input topic, it would be possible to distribute the computation.

### An algorithm to match any portion of the keywords without `contains`
For a better processing algorithm, but matching any portion of keywords, it might me
possible to alter some text algorithm to fit our needs.
Aho-Corasick allows to find efficiently multiple needles (searches) for one haystack
(keyword in our case), with an oriented graph counting occurences. With more time
(weeks, maybe months), it might be possible to use one needle for multiple haystacks.

### Using frameworks and databases
Using frameworks and databases, the best way to implement an autocomplete search would
be to use ElasticSearch.