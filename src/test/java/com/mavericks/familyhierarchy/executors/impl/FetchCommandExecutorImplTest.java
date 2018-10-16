package com.mavericks.familyhierarchy.executors.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mavericks.familyhierarchy.executors.FetchCommandExecutor;
import com.mavericks.familyhierarchy.generators.OutputGenerator;
import com.mavericks.familyhierarchy.models.ESex;
import com.mavericks.familyhierarchy.models.Person;
import com.mavericks.familyhierarchy.services.operations.PersonOperationsResolver;
import org.junit.After;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FetchCommandExecutorImplTest {
  private final PersonOperationsResolver mockPersonOperationsResolver = mock(PersonOperationsResolver.class);
  private final OutputGenerator mockOutputGenerator = mock(OutputGenerator.class);
  private final FetchCommandExecutor testFetchCommandExecutor = new FetchCommandExecutorImpl(mockPersonOperationsResolver, mockOutputGenerator);


  @After
  public void tearDown() {
    reset(mockPersonOperationsResolver, mockOutputGenerator);
  }

  @Test
  public void executeShouldBeAbleToExecuteFetchForHusband() {
    final String testInputCommand = "person=xyz relation=husband";
    final String testOutput = "test-output";
    final Person testPerson = new Person("testPerson", ESex.MALE, null, null);
    when(mockPersonOperationsResolver.getPartner(anyString(), any())).thenReturn(Optional.of(testPerson));
    when(mockOutputGenerator.getOutputForFetchRequest(anyString(), anyString(), any())).thenReturn(testOutput);

    final String actualOutput = testFetchCommandExecutor.execute(testInputCommand);

    verify(mockPersonOperationsResolver).getPartner("xyz", ESex.MALE);
    verify(mockOutputGenerator).getOutputForFetchRequest("xyz", "Husband", Collections.singletonList(testPerson));
    assertEquals(testOutput, actualOutput);
  }

  @Test
  public void executeShouldBeAbleToExecuteFetchForWife() {
    final String testInputCommand = "person=xyz relation=wife";
    final String testOutput = "test-output";
    final Person testPerson = new Person("testPerson", ESex.FEMALE, null, null);
    when(mockPersonOperationsResolver.getPartner(anyString(), any())).thenReturn(Optional.of(testPerson));
    when(mockOutputGenerator.getOutputForFetchRequest(anyString(), anyString(), any())).thenReturn(testOutput);

    final String actualOutput = testFetchCommandExecutor.execute(testInputCommand);

    verify(mockPersonOperationsResolver).getPartner("xyz", ESex.FEMALE);
    verify(mockOutputGenerator).getOutputForFetchRequest("xyz", "Wife", Collections.singletonList(testPerson));
    assertEquals(testOutput, actualOutput);
  }

  @Test
  public void executeShouldBeAbleToExecuteFetchForFather() {
    final String testInputCommand = "person=xyz relation=father";
    final String testOutput = "test-output";
    final Person testPerson = new Person("testPerson", ESex.FEMALE, null, null);
    when(mockPersonOperationsResolver.getParent(anyString(), any())).thenReturn(Optional.of(testPerson));
    when(mockOutputGenerator.getOutputForFetchRequest(anyString(), anyString(), any())).thenReturn(testOutput);

    final String actualOutput = testFetchCommandExecutor.execute(testInputCommand);

    verify(mockPersonOperationsResolver).getParent("xyz", ESex.MALE);
    verify(mockOutputGenerator).getOutputForFetchRequest("xyz", "Father", Collections.singletonList(testPerson));
    assertEquals(testOutput, actualOutput);
  }

  @Test
  public void executeShouldBeAbleToExecuteFetchForMother() {
    final String testInputCommand = "person=xyz relation=mother";
    final String testOutput = "test-output";
    final Person testPerson = new Person("testPerson", ESex.FEMALE, null, null);
    when(mockPersonOperationsResolver.getParent(anyString(), any())).thenReturn(Optional.of(testPerson));
    when(mockOutputGenerator.getOutputForFetchRequest(anyString(), anyString(), any())).thenReturn(testOutput);

    final String actualOutput = testFetchCommandExecutor.execute(testInputCommand);

    verify(mockPersonOperationsResolver).getParent("xyz", ESex.FEMALE);
    verify(mockOutputGenerator).getOutputForFetchRequest("xyz", "Mother", Collections.singletonList(testPerson));
    assertEquals(testOutput, actualOutput);
  }

  @Test
  public void executeShouldBeAbleToExecuteFetchForBrothers() {
    final String testInputCommand = "person=xyz relation=brothers";
    final String testOutput = "test-output";
    final List<Person> testPersons = Arrays.asList(
        new Person("testPerson1", ESex.MALE, null, null),
        new Person("testPerson2", ESex.MALE, null, null));
    when(mockPersonOperationsResolver.getSiblings(anyString(), any())).thenReturn(testPersons);
    when(mockOutputGenerator.getOutputForFetchRequest(anyString(), anyString(), any())).thenReturn(testOutput);

    final String actualOutput = testFetchCommandExecutor.execute(testInputCommand);

    verify(mockPersonOperationsResolver).getSiblings("xyz", ESex.MALE);
    verify(mockOutputGenerator).getOutputForFetchRequest("xyz", "Brothers", testPersons);
    assertEquals(testOutput, actualOutput);
  }

  @Test
  public void executeShouldBeAbleToExecuteFetchForBrother() {
    final String testInputCommand = "person=xyz relation=brother";
    final String testOutput = "test-output";
    final List<Person> testPerson = Collections.singletonList(new Person("testPerson", ESex.MALE, null, null));
    when(mockPersonOperationsResolver.getSiblings(anyString(), any())).thenReturn(testPerson);
    when(mockOutputGenerator.getOutputForFetchRequest(anyString(), any(), any())).thenReturn(testOutput);

    final String actualOutput = testFetchCommandExecutor.execute(testInputCommand);

    verify(mockPersonOperationsResolver).getSiblings("xyz", ESex.MALE);
    verify(mockOutputGenerator).getOutputForFetchRequest("xyz", "Brother", testPerson);
    assertEquals(testOutput, actualOutput);
  }

  @Test
  public void executeShouldBeAbleToExecuteFetchForSisters() {
    final String testInputCommand = "person=xyz relation=sisters";
    final String testOutput = "test-output";
    final List<Person> testPersons = Arrays.asList(
        new Person("testPerson1", ESex.FEMALE, null, null),
        new Person("testPerson2", ESex.FEMALE, null, null));
    when(mockPersonOperationsResolver.getSiblings(anyString(), any())).thenReturn(testPersons);
    when(mockOutputGenerator.getOutputForFetchRequest(anyString(), any(), any())).thenReturn(testOutput);

    final String actualOutput = testFetchCommandExecutor.execute(testInputCommand);

    verify(mockPersonOperationsResolver).getSiblings("xyz", ESex.FEMALE);
    verify(mockOutputGenerator).getOutputForFetchRequest("xyz", "Sisters", testPersons);
    assertEquals(testOutput, actualOutput);
  }

  @Test
  public void executeShouldBeAbleToExecuteFetchForSister() {
    final String testInputCommand = "person=xyz relation=sister";
    final String testOutput = "test-output";
    final List<Person> testPerson = Collections.singletonList(new Person("testPerson", ESex.FEMALE, null, null));
    when(mockPersonOperationsResolver.getSiblings(anyString(), any())).thenReturn(testPerson);
    when(mockOutputGenerator.getOutputForFetchRequest(anyString(), any(), any())).thenReturn(testOutput);

    final String actualOutput = testFetchCommandExecutor.execute(testInputCommand);

    verify(mockPersonOperationsResolver).getSiblings("xyz", ESex.FEMALE);
    verify(mockOutputGenerator).getOutputForFetchRequest("xyz", "Sister", testPerson);
    assertEquals(testOutput, actualOutput);
  }

  @Test
  public void executeShouldBeAbleToExecuteFetchForSon() {
    final String testInputCommand = "person=xyz relation=son";
    final String testOutput = "test-output";
    final List<Person> testPerson = Collections.singletonList(new Person("testPerson", ESex.MALE, null, null));
    when(mockPersonOperationsResolver.getChildren(anyString(), any())).thenReturn(testPerson);
    when(mockOutputGenerator.getOutputForFetchRequest(anyString(), any(), any())).thenReturn(testOutput);

    final String actualOutput = testFetchCommandExecutor.execute(testInputCommand);

    verify(mockPersonOperationsResolver).getChildren("xyz", ESex.MALE);
    verify(mockOutputGenerator).getOutputForFetchRequest("xyz", "Son", testPerson);
    assertEquals(testOutput, actualOutput);
  }

  @Test
  public void executeShouldBeAbleToExecuteFetchForSons() {
    final String testInputCommand = "person=xyz relation=sons";
    final String testOutput = "test-output";
    final List<Person> testPersons = Arrays.asList(
        new Person("testPerson1", ESex.MALE, null, null),
        new Person("testPerson2", ESex.MALE, null, null));
    when(mockPersonOperationsResolver.getChildren(anyString(), any())).thenReturn(testPersons);
    when(mockOutputGenerator.getOutputForFetchRequest(anyString(), any(), any())).thenReturn(testOutput);

    final String actualOutput = testFetchCommandExecutor.execute(testInputCommand);

    verify(mockPersonOperationsResolver).getChildren("xyz", ESex.MALE);
    verify(mockOutputGenerator).getOutputForFetchRequest("xyz", "Sons", testPersons);
    assertEquals(testOutput, actualOutput);
  }

  @Test
  public void executeShouldBeAbleToExecuteFetchForDaughter() {
    final String testInputCommand = "person=xyz relation=daughter";
    final String testOutput = "test-output";
    final List<Person> testPerson = Collections.singletonList(new Person("testPerson", ESex.FEMALE, null, null));
    when(mockPersonOperationsResolver.getChildren(anyString(), any())).thenReturn(testPerson);
    when(mockOutputGenerator.getOutputForFetchRequest(anyString(), any(), any())).thenReturn(testOutput);

    final String actualOutput = testFetchCommandExecutor.execute(testInputCommand);

    verify(mockPersonOperationsResolver).getChildren("xyz", ESex.FEMALE);
    verify(mockOutputGenerator).getOutputForFetchRequest("xyz", "Daughter", testPerson);
    assertEquals(testOutput, actualOutput);
  }

  @Test
  public void executeShouldBeAbleToExecuteFetchForDaughters() {
    final String testInputCommand = "person=xyz relation=daughters";
    final String testOutput = "test-output";
    final List<Person> testPersons = Arrays.asList(
        new Person("testPerson1", ESex.FEMALE, null, null),
        new Person("testPerson2", ESex.FEMALE, null, null));
    when(mockPersonOperationsResolver.getChildren(anyString(), any())).thenReturn(testPersons);
    when(mockOutputGenerator.getOutputForFetchRequest(anyString(), any(), any())).thenReturn(testOutput);

    final String actualOutput = testFetchCommandExecutor.execute(testInputCommand);

    verify(mockPersonOperationsResolver).getChildren("xyz", ESex.FEMALE);
    verify(mockOutputGenerator).getOutputForFetchRequest("xyz", "Daughters", testPersons);
    assertEquals(testOutput, actualOutput);
  }

  @Test
  public void executeShouldBeAbleToExecuteFetchForGrandmother() {
    final String testInputCommand = "person=xyz relation=grandmother";
    final String testOutput = "test-output";
    final Person testPerson = new Person("testPerson2", ESex.FEMALE, null, null);
    when(mockPersonOperationsResolver.getGrandParent(anyString(), any())).thenReturn(Optional.of(testPerson));
    when(mockOutputGenerator.getOutputForFetchRequest(anyString(), any(), any())).thenReturn(testOutput);

    final String actualOutput = testFetchCommandExecutor.execute(testInputCommand);

    verify(mockPersonOperationsResolver).getGrandParent("xyz", ESex.FEMALE);
    verify(mockOutputGenerator).getOutputForFetchRequest("xyz", "Grandmother", Collections.singletonList(testPerson));
    assertEquals(testOutput, actualOutput);
  }

  @Test
  public void executeShouldBeAbleToExecuteFetchForGrandfather() {
    final String testInputCommand = "person=xyz relation=grandfather";
    final String testOutput = "test-output";
    final Person testPerson = new Person("testPerson2", ESex.FEMALE, null, null);
    when(mockPersonOperationsResolver.getGrandParent(anyString(), any())).thenReturn(Optional.of(testPerson));
    when(mockOutputGenerator.getOutputForFetchRequest(anyString(), any(), any())).thenReturn(testOutput);

    final String actualOutput = testFetchCommandExecutor.execute(testInputCommand);

    verify(mockPersonOperationsResolver).getGrandParent("xyz", ESex.MALE);
    verify(mockOutputGenerator).getOutputForFetchRequest("xyz", "Grandfather", Collections.singletonList(testPerson));
    assertEquals(testOutput, actualOutput);
  }

  @Test
  public void executeShouldBeAbleToExecuteFetchForCousin() {
    final String testInputCommand = "person=xyz relation=cousin";
    final String testOutput = "test-output";
    final List<Person> testPersons = Collections.singletonList(new Person("testPerson2", ESex.FEMALE, null, null));
    when(mockPersonOperationsResolver.getCousins(anyString())).thenReturn(testPersons);
    when(mockOutputGenerator.getOutputForFetchRequest(anyString(), any(), any())).thenReturn(testOutput);

    final String actualOutput = testFetchCommandExecutor.execute(testInputCommand);

    verify(mockPersonOperationsResolver).getCousins("xyz");
    verify(mockOutputGenerator).getOutputForFetchRequest("xyz", "Cousin", testPersons);
    assertEquals(testOutput, actualOutput);
  }

  @Test
  public void executeShouldBeAbleToExecuteFetchForCousins() {
    final String testInputCommand = "person=xyz relation=cousins";
    final String testOutput = "test-output";
    final List<Person> testPersons = Arrays.asList(
        new Person("testPerson1", ESex.FEMALE, null, null),
        new Person("testPerson2", ESex.FEMALE, null, null));
    when(mockPersonOperationsResolver.getCousins(anyString())).thenReturn(testPersons);
    when(mockOutputGenerator.getOutputForFetchRequest(anyString(), any(), any())).thenReturn(testOutput);

    final String actualOutput = testFetchCommandExecutor.execute(testInputCommand);

    verify(mockPersonOperationsResolver).getCousins("xyz");
    verify(mockOutputGenerator).getOutputForFetchRequest("xyz", "Cousins", testPersons);
    assertEquals(testOutput, actualOutput);
  }

  @Test
  public void executeShouldBeAbleToExecuteFetchForAunt() {
    final String testInputCommand = "person=xyz relation=aunt";
    final String testOutput = "test-output";
    final List<Person> testPersons = Collections.singletonList(new Person("testPerson2", ESex.FEMALE, null, null));
    when(mockPersonOperationsResolver.getAuntsOrUncles(anyString(), any())).thenReturn(testPersons);
    when(mockOutputGenerator.getOutputForFetchRequest(anyString(), any(), any())).thenReturn(testOutput);

    final String actualOutput = testFetchCommandExecutor.execute(testInputCommand);

    verify(mockPersonOperationsResolver).getAuntsOrUncles("xyz", ESex.FEMALE);
    verify(mockOutputGenerator).getOutputForFetchRequest("xyz", "Aunt", testPersons);
    assertEquals(testOutput, actualOutput);
  }

  @Test
  public void executeShouldBeAbleToExecuteFetchForAunts() {
    final String testInputCommand = "person=xyz relation=aunts";
    final String testOutput = "test-output";
    final List<Person> testPersons = Arrays.asList(
        new Person("testPerson1", ESex.FEMALE, null, null),
        new Person("testPerson2", ESex.FEMALE, null, null));
    when(mockPersonOperationsResolver.getAuntsOrUncles(anyString(), any())).thenReturn(testPersons);
    when(mockOutputGenerator.getOutputForFetchRequest(anyString(), any(), any())).thenReturn(testOutput);

    final String actualOutput = testFetchCommandExecutor.execute(testInputCommand);

    verify(mockPersonOperationsResolver).getAuntsOrUncles("xyz", ESex.FEMALE);
    verify(mockOutputGenerator).getOutputForFetchRequest("xyz", "Aunts", testPersons);
    assertEquals(testOutput, actualOutput);
  }

  @Test
  public void executeShouldBeAbleToExecuteFetchForUncle() {
    final String testInputCommand = "person=xyz relation=uncle";
    final String testOutput = "test-output";
    final List<Person> testPersons = Collections.singletonList(new Person("testPerson2", ESex.MALE, null, null));
    when(mockPersonOperationsResolver.getAuntsOrUncles(anyString(), any())).thenReturn(testPersons);
    when(mockOutputGenerator.getOutputForFetchRequest(anyString(), any(), any())).thenReturn(testOutput);

    final String actualOutput = testFetchCommandExecutor.execute(testInputCommand);

    verify(mockPersonOperationsResolver).getAuntsOrUncles("xyz", ESex.MALE);
    verify(mockOutputGenerator).getOutputForFetchRequest("xyz", "Uncle", testPersons);
    assertEquals(testOutput, actualOutput);
  }

  @Test
  public void executeShouldBeAbleToExecuteFetchForUncles() {
    final String testInputCommand = "person=xyz relation=uncles";
    final String testOutput = "test-output";
    final List<Person> testPersons = Arrays.asList(
        new Person("testPerson1", ESex.MALE, null, null),
        new Person("testPerson2", ESex.MALE, null, null));
    when(mockPersonOperationsResolver.getAuntsOrUncles(anyString(), any())).thenReturn(testPersons);
    when(mockOutputGenerator.getOutputForFetchRequest(anyString(), any(), any())).thenReturn(testOutput);

    final String actualOutput = testFetchCommandExecutor.execute(testInputCommand);

    verify(mockPersonOperationsResolver).getAuntsOrUncles("xyz", ESex.MALE);
    verify(mockOutputGenerator).getOutputForFetchRequest("xyz", "Uncles", testPersons);
    assertEquals(testOutput, actualOutput);
  }

  @Test
  public void executeShouldBeAbleToExecuteFetchForGrandson() {
    final String testInputCommand = "person=xyz relation=grandson";
    final String testOutput = "test-output";
    final List<Person> testPersons = Collections.singletonList(new Person("testPerson2", ESex.MALE, null, null));
    when(mockPersonOperationsResolver.getGrandChildren(anyString(), any())).thenReturn(testPersons);
    when(mockOutputGenerator.getOutputForFetchRequest(anyString(), any(), any())).thenReturn(testOutput);

    final String actualOutput = testFetchCommandExecutor.execute(testInputCommand);

    verify(mockPersonOperationsResolver).getGrandChildren("xyz", ESex.MALE);
    verify(mockOutputGenerator).getOutputForFetchRequest("xyz", "Grandson", testPersons);
    assertEquals(testOutput, actualOutput);
  }

  @Test
  public void executeShouldBeAbleToExecuteFetchForGrandsons() {
    final String testInputCommand = "person=xyz relation=grandsons";
    final String testOutput = "test-output";
    final List<Person> testPersons = Arrays.asList(
        new Person("testPerson1", ESex.MALE, null, null),
        new Person("testPerson2", ESex.MALE, null, null));
    when(mockPersonOperationsResolver.getGrandChildren(anyString(), any())).thenReturn(testPersons);
    when(mockOutputGenerator.getOutputForFetchRequest(anyString(), any(), any())).thenReturn(testOutput);

    final String actualOutput = testFetchCommandExecutor.execute(testInputCommand);

    verify(mockPersonOperationsResolver).getGrandChildren("xyz", ESex.MALE);
    verify(mockOutputGenerator).getOutputForFetchRequest("xyz", "Grandsons", testPersons);
    assertEquals(testOutput, actualOutput);
  }

  @Test
  public void executeShouldBeAbleToExecuteFetchForGranddaughter() {
    final String testInputCommand = "person=xyz relation=granddaughter";
    final String testOutput = "test-output";
    final List<Person> testPersons = Collections.singletonList(new Person("testPerson2", ESex.FEMALE, null, null));
    when(mockPersonOperationsResolver.getGrandChildren(anyString(), any())).thenReturn(testPersons);
    when(mockOutputGenerator.getOutputForFetchRequest(anyString(), any(), any())).thenReturn(testOutput);

    final String actualOutput = testFetchCommandExecutor.execute(testInputCommand);

    verify(mockPersonOperationsResolver).getGrandChildren("xyz", ESex.FEMALE);
    verify(mockOutputGenerator).getOutputForFetchRequest("xyz", "Granddaughter", testPersons);
    assertEquals(testOutput, actualOutput);
  }

  @Test
  public void executeShouldBeAbleToExecuteFetchForGranddaughters() {
    final String testInputCommand = "person=xyz relation=granddaughters";
    final String testOutput = "test-output";
    final List<Person> testPersons = Arrays.asList(
        new Person("testPerson1", ESex.FEMALE, null, null),
        new Person("testPerson2", ESex.FEMALE, null, null));
    when(mockPersonOperationsResolver.getGrandChildren(anyString(), any())).thenReturn(testPersons);
    when(mockOutputGenerator.getOutputForFetchRequest(anyString(), any(), any())).thenReturn(testOutput);

    final String actualOutput = testFetchCommandExecutor.execute(testInputCommand);

    verify(mockPersonOperationsResolver).getGrandChildren("xyz", ESex.FEMALE);
    verify(mockOutputGenerator).getOutputForFetchRequest("xyz", "Granddaughters", testPersons);
    assertEquals(testOutput, actualOutput);
  }

  @Test
  public void executeShouldBeAbleToExecuteUnknownCommand() {
    final String testInputCommand = "test-unknown-command";
    final String testOutput = "unknown-output";
    when(mockOutputGenerator.getOutputForUnknownRequest(anyString())).thenReturn("unknown-output");

    final String actualOutput = testFetchCommandExecutor.execute(testInputCommand);

    verify(mockOutputGenerator).getOutputForUnknownRequest(testInputCommand);
    assertEquals(testOutput, actualOutput);
  }

  @Test
  public void executeShouldBeAbleToHandleUnknownRelation() {
    final String testInputCommand = "person=xyz relation=unknown";
    final String testOutput = "unknown-output";
    when(mockOutputGenerator.getOutputUnsupportedRelation(anyString())).thenReturn("unknown-output");

    final String actualOutput = testFetchCommandExecutor.execute(testInputCommand);

    verify(mockOutputGenerator).getOutputUnsupportedRelation("unknown");
    assertEquals(testOutput, actualOutput);
  }
}
