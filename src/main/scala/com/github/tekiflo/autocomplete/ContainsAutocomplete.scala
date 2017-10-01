package com.github.tekiflo.autocomplete

import org.apache.commons.lang3.StringUtils

/**
  * Naive Autocomplete using contains, searches for strings containing the
  * search string in keywords.
  */
object ContainsAutocomplete extends Autocomplete {
  /**
    * Searches for string str in keywords, then returns a list with sorted
    * keywords containing the key (maximum size of MAX_ITEMS).
    * StringUtils from Apache Commons Lang is used to search the string,
    * ignoring the case efficiently.
    *
    * @param str      the string to search.
    * @param keywords a list of strings to search into.
    * @return a list with keywords starting with / containing str.
    */
  override def search(str: String, keywords: List[String]): List[String] = {
    keywords
      .filter(StringUtils.containsIgnoreCase(_, str))
      .sorted
      .take(MAX_ITEMS)
  }
}
