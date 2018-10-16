package com.mavericks.familyhierarchy.services;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mavericks.familyhierarchy.io.IoOperations;
import com.mavericks.familyhierarchy.executors.CommandResolver;
import com.mavericks.familyhierarchy.utils.FileReader;
import com.typesafe.config.Config;
import org.junit.Test;

import java.util.Collections;

public class FileBasedDefaultFamilyLoaderTest {

  private final FileReader mockFileReader = mock(FileReader.class);
  private final IoOperations mockIoOperations = mock(IoOperations.class);
  private final CommandResolver commandResolver = mock(CommandResolver.class);
  private final Config mockConfig = mock(Config.class);

  @Test
  public void loadShouldCallExpectedMethods() {
    final String configPathFileName = "initial.file.name";
    final String testFileName = "test-file-name";
    final String testInputCommand = "test-input";
    final String testOutput = "test-output";
    when(mockConfig.getString(anyString())).thenReturn(testFileName);
    when(mockFileReader.getAllExceptEmptyLines(anyString())).thenReturn(Collections.singletonList(testInputCommand));
    when(commandResolver.resolve(anyString())).thenReturn(testOutput);
    doNothing().when(mockIoOperations).println(Collections.singletonList(testOutput));
    DefaultFamilyLoader defaultFamilyLoader = new FileBasedDefaultFamilyLoader(mockFileReader, mockIoOperations, commandResolver, mockConfig);

    defaultFamilyLoader.load();

    verify(mockConfig, times(1)).getString(configPathFileName);
    verify(mockFileReader, times(1)).getAllExceptEmptyLines(testFileName);
    verify(commandResolver, times(1)).resolve(testInputCommand);
  }
}
