package com.github.tekiflo.autocomplete

import com.github.tekiflo.autocomplete.ContainsAutocomplete.{MAX_ITEMS, search}
import com.github.tekiflo.autocomplete.Utils.SAMPLE_KEYWORDS
import org.scalatest._

/**
  * Test cases for ContainsAutocomplete.
  */
class ContainsAutocompleteSpec extends FlatSpec with Matchers {
  info("Starting...")

  "Autocomplete with startsWith" should "returns Nil if nothing matches" in {
    search("test", SAMPLE_KEYWORDS) shouldEqual Nil
  }

  it should s"returns less than $MAX_ITEMS if there aren't enough matching keywords" in {
    search("Proc", SAMPLE_KEYWORDS).size shouldEqual (2 min MAX_ITEMS)
  }

  it should "not be case sensitive" in {
    search("PaNDORA", SAMPLE_KEYWORDS) shouldEqual List("Pandora")
  }

  it should s"returns at most $MAX_ITEMS items" in {
    search("p", SAMPLE_KEYWORDS).size shouldEqual MAX_ITEMS
  }

  it should "returns a sorted list" in {
    search("p", SAMPLE_KEYWORDS) shouldEqual List("Pandora", "Paypal", "Pg&e", "Phone")
  }

  it should "returns keywords containing the searched string" in {
    search("owl", SAMPLE_KEYWORDS) shouldEqual List("Bowl", "Owl")
  }
}
