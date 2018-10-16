package com.mavericks.familyhierarchy.executors.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mavericks.familyhierarchy.executors.AddCommandExecutor;
import com.mavericks.familyhierarchy.generators.OutputGenerator;
import com.mavericks.familyhierarchy.models.ESex;
import com.mavericks.familyhierarchy.models.Person;
import com.mavericks.familyhierarchy.services.operations.PersonOperationsResolver;
import org.junit.Test;

import java.util.Optional;

public class AddCommandExecutorImplTest {

  private final PersonOperationsResolver mockPersonOperationsResolver = mock(PersonOperationsResolver.class);
  private final OutputGenerator mockOutputGenerator = mock(OutputGenerator.class);

  @Test
  public void executeShouldExecuteHusbandWifeMarriage() {
    final String inputCommand = "husband=male wife=female";
    final String testHusbandName = "male";
    final String testWifeName = "female";
    final String expectedOutput = "added female";

    runMarriageTest(inputCommand, testHusbandName, testWifeName, expectedOutput);
  }

  @Test
  public void executeShouldExecuteWifeHusbandMarriage() {
    final String inputCommand = "wife=female husband=male";
    final String testHusbandName = "male";
    final String testWifeName = "female";
    final String expectedOutput = "added male";
    runMarriageTest(inputCommand, testHusbandName, testWifeName, expectedOutput);
  }

  @Test
  public void executeShouldExecuteMotherSonMatcher() {
    final String inputCommand = "mother=female son=male";
    final String testSonName = "male";
    final String testMotherName = "female";
    final String expectedOutput = "added male";

    runAddChildTest(inputCommand, expectedOutput, testMotherName, testSonName, ESex.MALE);
  }

  @Test
  public void executeShouldExecuteSonMotherMatcher() {
    final String inputCommand = "son=male mother=female";
    final String testSonName = "male";
    final String testMotherName = "female";
    final String expectedOutput = "added male";

    runAddChildTest(inputCommand, expectedOutput, testMotherName, testSonName, ESex.MALE);
  }

  @Test
  public void executeShouldExecuteMotherDaughterMatcher() {
    final String inputCommand = "mother=female daughter=female_child";
    final String testDaughterName = "female_child";
    final String testMotherName = "female";
    final String expectedOutput = "added female";

    runAddChildTest(inputCommand, expectedOutput, testMotherName, testDaughterName, ESex.FEMALE);
  }

  @Test
  public void executeShouldExecuteDaughterMotherMatcher() {
    final String inputCommand = "daughter=female_child mother=female";
    final String testDaughterName = "female_child";
    final String testMotherName = "female";
    final String expectedOutput = "added female";

    runAddChildTest(inputCommand, expectedOutput, testMotherName, testDaughterName, ESex.FEMALE);
  }

  @Test
  public void executeShouldReturnNonExecutableRelation() {
    String testInvalidInput = "test-invalid-input";
    String testInvalidOutput = "test-invalid-output";
    AddCommandExecutor addCommandExecutor = new AddCommandExecutorImpl(mockPersonOperationsResolver, mockOutputGenerator);
    when(mockOutputGenerator.getOutputForUnknownRequest(any())).thenReturn(testInvalidOutput);

    String actualOutput = addCommandExecutor.execute(testInvalidInput);

    verify(mockOutputGenerator).getOutputForUnknownRequest(testInvalidInput);
    assertEquals(testInvalidOutput, actualOutput);
  }

  private void runMarriageTest(String inputCommand, String testHusbandName, String testWifeName, String expectedOutput) {
    final Optional<Person> testOptWife = Optional.of(new Person("female", ESex.FEMALE, null, null));
    when(mockPersonOperationsResolver.addMarriage(anyString(), anyString())).thenReturn(testOptWife);
    when(mockOutputGenerator.getOutputForMarriageRequest(any(), anyString(), anyString())).thenReturn(expectedOutput);
    AddCommandExecutor addCommandExecutor = new AddCommandExecutorImpl(mockPersonOperationsResolver, mockOutputGenerator);

    String actualOutput = addCommandExecutor.execute(inputCommand);

    verify(mockOutputGenerator).getOutputForMarriageRequest(testOptWife, testHusbandName, testWifeName);
    verify(mockPersonOperationsResolver).addMarriage(testHusbandName, testWifeName);
    assertEquals(expectedOutput, actualOutput);
  }

  private void runAddChildTest(String inputCommand, String expectedOutput, String testMotherName, String testChildName, ESex
      childSex) {
    final Optional<Person> testOptChild = Optional.of(new Person(testChildName, ESex.MALE, null, null));
    when(mockPersonOperationsResolver.addChild(anyString(), anyString(), any())).thenReturn(testOptChild);
    when(mockOutputGenerator.getOutputForNewBornRequest(any(), anyString(), anyString())).thenReturn(expectedOutput);
    AddCommandExecutor addCommandExecutor = new AddCommandExecutorImpl(mockPersonOperationsResolver, mockOutputGenerator);

    String actualOutput = addCommandExecutor.execute(inputCommand);

    verify(mockOutputGenerator).getOutputForNewBornRequest(testOptChild, testMotherName, testChildName);
    verify(mockPersonOperationsResolver).addChild(testMotherName, testChildName, childSex);
    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void shouldResolvePersonAndSpouseIntoRelations() {
    String testPartner1 = "Shawn";
    String testPartner2 = "Michael";
    String expectedOutput = "Welcome to family, " + testPartner2 + "!";
    String inputCommand = "Person="+testPartner1+" Spouse=" + testPartner2;
    Optional<Person> optTestPerson2 = Optional.of(new Person(testPartner2, ESex.MALE, null, null));
    when(mockPersonOperationsResolver.addMarriage(anyString(), anyString())).thenReturn(optTestPerson2);
    when(mockOutputGenerator.getOutputForMarriageRequest(any(), anyString(), anyString())).thenReturn(expectedOutput);

    AddCommandExecutor addCommandExecutor = new AddCommandExecutorImpl(mockPersonOperationsResolver, mockOutputGenerator);
    String actualOutput = addCommandExecutor.execute(inputCommand);

    verify(mockPersonOperationsResolver).addMarriage(testPartner1.toLowerCase(),testPartner2.toLowerCase());
    verify(mockOutputGenerator).getOutputForMarriageRequest(optTestPerson2, testPartner1, testPartner2);

    assertEquals(expectedOutput, actualOutput);
  }
}