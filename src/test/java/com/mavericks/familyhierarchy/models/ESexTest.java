package com.mavericks.familyhierarchy.models;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ESexTest {

  @Test
  public void getOppSexShouldReturnMaleIfFemaleIsPassed() {
    ESex actualSex = ESex.getOppSex(ESex.FEMALE);

    assertEquals(ESex.MALE, actualSex);
  }

  @Test
  public void getOppSexShouldReturnFemaleIfmaleIsPassed() {
    ESex actualSex = ESex.getOppSex(ESex.MALE);

    assertEquals(ESex.FEMALE, actualSex);
  }
}
