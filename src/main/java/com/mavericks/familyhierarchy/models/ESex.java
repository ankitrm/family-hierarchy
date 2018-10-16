package com.mavericks.familyhierarchy.models;

public enum ESex {
  MALE, FEMALE;

  public static ESex getOppSex(ESex sex) {
    if (sex == ESex.FEMALE)
      return ESex.MALE;
    else
      return ESex.FEMALE;
  }
}
