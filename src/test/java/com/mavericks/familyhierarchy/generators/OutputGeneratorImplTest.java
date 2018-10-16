package com.mavericks.familyhierarchy.generators;

import static org.junit.Assert.assertEquals;

import com.mavericks.familyhierarchy.models.ESex;
import com.mavericks.familyhierarchy.models.Person;
import org.junit.Test;

import java.util.Collections;
import java.util.Optional;

public class OutputGeneratorImplTest {

  private static final OutputGenerator OUTPUT_GENERATOR = new OutputGeneratorImpl();

  @Test
  public void getOutputForFetchRequestShouldReturnResponseForNoPeopleFound() {
    final String expectedOutput = "Please check person `abc` or relation `test-relation`";

    final String actualOutput = OUTPUT_GENERATOR.getOutputForFetchRequest("abc", "test-relation", Collections.emptyList());

    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void getOutputForFetchRequestShouldReturnResponseForPeopleFound() {
    final String expectedOutput = "test-relation=Test-person";
    final Person testPerson = new Person("test-person", ESex.MALE, null, null);

    final String actualOutput = OUTPUT_GENERATOR.getOutputForFetchRequest("abc", "test-relation", Collections.singletonList(testPerson));

    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void getOutputForUnknownRequestShouldReturnExpectedResponse() {
    final String expectedOutput = "Please check the request again: invalid=person fake=relation";

    final String actualOutput = OUTPUT_GENERATOR.getOutputForUnknownRequest("invalid=person fake=relation");

    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void getOutputUnsupportedRelationShouldReturnExpectedResponse() {
    final String expectedOutput = "We do not support this 'fake-relation' relation right now";

    final String actualOutput = OUTPUT_GENERATOR.getOutputUnsupportedRelation("fake-relation");

    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void getOutputForMarriageRequestShouldReturnResponseIfMarriageFails() {
    final String expectedOutput = "Either members (fake-wife, fake-husband) already exist or do not exist family to marry";

    final String actualOutput = OUTPUT_GENERATOR.getOutputForMarriageRequest(Optional.empty(), "fake-wife", "fake-husband");

    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void getOutputForMarriageRequestShouldReturnResponseIfMarriagePasses() {
    final String expectedOutput = "Welcome to family, New-person!";
    final Person newPerson = new Person("new-person", ESex.MALE, null, null);

    final String actualOutput = OUTPUT_GENERATOR.getOutputForMarriageRequest(Optional.of(newPerson), "fake-wife", "fake-husband");

    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void getOutputForNewBornRequestShouldReturnFalseIfNoNewPersonFound() {
    final String expectedOutput = "We could not find Mother (test-mother) in family to add (fake-child)";

    final String actualOutput = OUTPUT_GENERATOR.getOutputForNewBornRequest(Optional.empty(), "test-mother", "fake-child");

    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void getOutputForNewBornRequestShouldReturnFalseIfNewPersonIsFound() {
    final String expectedOutput = "Welcome to family, New-child!";
    final Person newChild = new Person("new-child", ESex.MALE, null, null);

    final String actualOutput = OUTPUT_GENERATOR.getOutputForNewBornRequest(Optional.of(newChild), "fake-wife", "new-child");

    assertEquals(expectedOutput, actualOutput);
  }
}
