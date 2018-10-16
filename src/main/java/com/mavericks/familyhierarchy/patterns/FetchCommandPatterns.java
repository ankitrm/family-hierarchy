package com.mavericks.familyhierarchy.patterns;

import java.util.regex.Pattern;

public class FetchCommandPatterns {
  public static final Pattern PATTERN_FETCH_PERSON_FROM_RELATION = Pattern.compile("^person=(\\w+)\\s+relation=(\\w+)",
      Pattern.CASE_INSENSITIVE);
}
