package com.mavericks.familyhierarchy.services;

import com.mavericks.familyhierarchy.io.IoOperations;
import com.mavericks.familyhierarchy.executors.CommandResolver;
import com.mavericks.familyhierarchy.utils.FileReader;
import com.typesafe.config.Config;

import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;

public class FileBasedDefaultFamilyLoader implements DefaultFamilyLoader {
  private final FileReader fileReader;
  private final IoOperations ioOperations;
  private final CommandResolver commandResolver;
  private final Config config;

  @Inject
  FileBasedDefaultFamilyLoader(FileReader fileReader,
                               IoOperations ioOperations,
                               CommandResolver commandResolver,
                               Config config) {
    this.fileReader = fileReader;
    this.ioOperations = ioOperations;
    this.commandResolver = commandResolver;
    this.config = config;
  }

  @Override
  public void load() {
    final String fileName = config.getString("initial.file.name");
    final List<String> defaultCommandsFromFile = fileReader.getAllExceptEmptyLines(fileName);
    if (defaultCommandsFromFile.isEmpty()) {
      ioOperations.printNoDefaultFamilyTree(fileName);
    } else {
      final List<String> results = defaultCommandsFromFile
          .stream()
          .map(commandResolver::resolve).collect(Collectors.toList());
      ioOperations.println(results);
    }
  }
}
