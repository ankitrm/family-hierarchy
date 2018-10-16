package com.mavericks.familyhierarchy.app;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mavericks.familyhierarchy.io.IoOperations;
import com.mavericks.familyhierarchy.executors.CommandResolver;
import com.mavericks.familyhierarchy.services.DefaultFamilyLoader;
import com.typesafe.config.Config;
import org.junit.After;
import org.junit.Test;

public class FamilyHierarchyApplicationTest {

  private final DefaultFamilyLoader mockDefaultFamilyReader = mock(DefaultFamilyLoader.class);
  private final IoOperations mockIoOperations = mock(IoOperations.class);
  private final CommandResolver mockCommandResolver = mock(CommandResolver.class);
  private final Config mockConfig = mock(Config.class);

  @After
  public void tearDown() {
    reset(mockDefaultFamilyReader, mockIoOperations, mockCommandResolver, mockConfig);
  }

  @Test
  public void startShouldNotLoadInitialTree() {
    final String testInputCommand = "test-command";
    final FamilyHierarchyApplication mainApplication =
        new FamilyHierarchyApplication(mockDefaultFamilyReader, mockIoOperations, mockCommandResolver, mockConfig);
    when(mockConfig.getBoolean("load.default.family")).thenReturn(false);
    when(mockConfig.getString("patterns.exit")).thenReturn("^q|quit|exit");
    when(mockIoOperations.getUserRequest()).thenReturn(testInputCommand, "q");
    when(mockCommandResolver.resolve(any())).thenReturn("test-output");
    doNothing().when(mockIoOperations).println("test-output");
    doNothing().when(mockIoOperations).println("Please enter a command to start operations (quit/exit/q to exit):");

    mainApplication.start();

    verify(mockCommandResolver, times(1)).resolve(testInputCommand);
  }

  @Test
  public void startShouldLoadInitialTree() {
    final String testInputCommand = "test-command";
    final FamilyHierarchyApplication mainApplication =
        new FamilyHierarchyApplication(mockDefaultFamilyReader, mockIoOperations, mockCommandResolver, mockConfig);
    when(mockConfig.getBoolean("load.default.family")).thenReturn(true);
    doNothing().when(mockDefaultFamilyReader).load();
    when(mockConfig.getString("patterns.exit")).thenReturn("^q|quit|exit");
    when(mockIoOperations.getUserRequest()).thenReturn(testInputCommand, "q");
    when(mockCommandResolver.resolve(any())).thenReturn("test-output");
    doNothing().when(mockIoOperations).println("test-output");
    doNothing().when(mockIoOperations).println("Please enter a command to start operations (quit/exit/q to exit):");

    mainApplication.start();

    verify(mockCommandResolver, times(1)).resolve(testInputCommand);
  }
}