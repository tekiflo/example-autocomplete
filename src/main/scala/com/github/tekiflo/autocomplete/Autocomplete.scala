package com.github.tekiflo.autocomplete

/**
  * Auto-completion for a String, using a list of keywords.
  */
trait Autocomplete {
  /**
    * Max items to return from search function.
    */
  val MAX_ITEMS = 4

  /**
    * Searches for string str in keywords, then returns a list with sorted
    * keywords starting with / containing the key (maximum size of MAX_ITEMS).
    * The search is case insensitive.
    *
    * @param str      the string to search.
    * @param keywords a list of strings to search into.
    * @return a list with keywords starting with / containing str.
    */
  def search(str: String, keywords: List[String]): List[String]
}
