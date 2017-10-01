package com.github.tekiflo.autocomplete

import org.scalatest._
import StartsWithAutocomplete._
import Utils.SAMPLE_KEYWORDS

/**
  * Test cases for StartsWithAutocomplete.
  */
class StartsWithAutocompleteSpec extends FlatSpec with Matchers {
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
}
