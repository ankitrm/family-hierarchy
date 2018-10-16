package com.mavericks.familyhierarchy.patterns;

import java.util.regex.Pattern;

public class AddCommandPatterns {
  private static final String REGEX_MOTHER_SON = "^mother=(\\w+)\\s+son=(\\w+)";
  private static final String REGEX_SON_MOTHER = "^son=(\\w+)\\s+mother=(\\w+)";
  private static final String REGEX_MOTHER_DAUGHTER = "^mother=(\\w+)\\s+daughter=(\\w+)";
  private static final String REGEX_DAUGHTER_MOTHER = "^daughter=(\\w+)\\s+mother=(\\w+)";
  private static final String REGEX_HUSBAND_WIFE = "^husband=(\\w+)\\s+wife=(\\w+)";
  private static final String REGEX_WIFE_HUSBAND = "^wife=(\\w+)\\s+husband=(\\w+)";
  private static final String REGEX_PERSON_SPOUSE = "^person=(\\w+)\\s+spouse=(\\w+)";

  // Any newly introduced pattern must be be added to the supported regex
  private static final String SUPPORTED_ADD_REGEX =
      "(" + REGEX_MOTHER_SON + "|" + REGEX_SON_MOTHER + "|" + REGEX_MOTHER_DAUGHTER + "|" + REGEX_DAUGHTER_MOTHER + "|" +
          REGEX_HUSBAND_WIFE + "|" + REGEX_WIFE_HUSBAND + "|" + REGEX_PERSON_SPOUSE +")";

  public static final Pattern SUPPORTED_ADD_PATTERNS = Pattern.compile(SUPPORTED_ADD_REGEX, Pattern.CASE_INSENSITIVE);
  public static final Pattern PATTERN_INSERT_MOTHER_SON = Pattern.compile(REGEX_MOTHER_SON, Pattern.CASE_INSENSITIVE);
  public static final Pattern PATTERN_INSERT_SON_MOTHER = Pattern.compile(REGEX_SON_MOTHER, Pattern.CASE_INSENSITIVE);
  public static final Pattern PATTERN_INSERT_MOTHER_DAUGHTER = Pattern.compile(REGEX_MOTHER_DAUGHTER, Pattern.CASE_INSENSITIVE);
  public static final Pattern PATTERN_INSERT_DAUGHTER_MOTHER = Pattern.compile(REGEX_DAUGHTER_MOTHER, Pattern.CASE_INSENSITIVE);
  public static final Pattern PATTERN_INSERT_HUSBAND_WIFE = Pattern.compile(REGEX_HUSBAND_WIFE, Pattern.CASE_INSENSITIVE);
  public static final Pattern PATTERN_INSERT_WIFE_HUSBAND = Pattern.compile(REGEX_WIFE_HUSBAND, Pattern.CASE_INSENSITIVE);
  public static final Pattern PATTERN_INSERT_PERSON_SPOUSE = Pattern.compile(REGEX_PERSON_SPOUSE, Pattern.CASE_INSENSITIVE);
}
