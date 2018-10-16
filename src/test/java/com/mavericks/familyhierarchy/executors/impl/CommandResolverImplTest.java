package com.mavericks.familyhierarchy.executors.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mavericks.familyhierarchy.executors.AddCommandExecutor;
import com.mavericks.familyhierarchy.executors.CommandResolver;
import com.mavericks.familyhierarchy.executors.FetchCommandExecutor;
import com.mavericks.familyhierarchy.generators.OutputGenerator;
import org.junit.After;
import org.junit.Test;

public class CommandResolverImplTest {

  private final FetchCommandExecutor mockFetchCommandExecutor = mock(FetchCommandExecutor.class);
  private final AddCommandExecutor mockAddCommandExecutor = mock(AddCommandExecutor.class);
  private final OutputGenerator mockOutputGenerator = mock(OutputGenerator.class);

  @After
  public void tearDown() {
    reset(mockFetchCommandExecutor, mockAddCommandExecutor, mockOutputGenerator);
  }

  @Test
  public void resolveShouldResolveFetchCommand() {
    final CommandResolver commandResolver = new CommandResolverImpl(mockFetchCommandExecutor, mockAddCommandExecutor, mockOutputGenerator);
    final String testValidInputForFetch = "person=fakePerson relation=fakeRelation";
    final String expectedOutputForFetch = "test-output-from-fetch";
    when(mockFetchCommandExecutor.execute(anyString())).thenReturn(expectedOutputForFetch);

    String actualOutputForFetch = commandResolver.resolve(testValidInputForFetch);

    verify(mockFetchCommandExecutor, times(1)).execute(testValidInputForFetch);
    assertEquals(actualOutputForFetch, expectedOutputForFetch);

  }

  @Test
  public void resolveShouldResolveAddCommand() {
    final CommandResolver commandResolver = new CommandResolverImpl(mockFetchCommandExecutor, mockAddCommandExecutor, mockOutputGenerator);
    final String testValidInputForAddition = "husband=fakeMale wife=fakeFemale";
    final String expectedOutputFromAdd = "test-output-from-add";
    when(mockAddCommandExecutor.execute(anyString())).thenReturn(expectedOutputFromAdd);

    String actualOutputForAddition = commandResolver.resolve(testValidInputForAddition);

    verify(mockAddCommandExecutor).execute(testValidInputForAddition);
    assertEquals(actualOutputForAddition, expectedOutputFromAdd);
  }

  @Test
  public void resolveShouldResolveUnknownRequest() {
    final CommandResolver commandResolver = new CommandResolverImpl(mockFetchCommandExecutor, mockAddCommandExecutor, mockOutputGenerator);
    final String testInvalidInput = "unknown-fake-command";
    final String actualOutputForUnknownCommand = "test-output-from-unknown-fake-command";
    when(mockOutputGenerator.getOutputForUnknownRequest(anyString())).thenReturn(actualOutputForUnknownCommand);

    String expectedOutputForUnknownRequest = commandResolver.resolve(testInvalidInput);

    verify(mockOutputGenerator).getOutputForUnknownRequest(testInvalidInput);
    assertEquals(expectedOutputForUnknownRequest, actualOutputForUnknownCommand);
  }

}
