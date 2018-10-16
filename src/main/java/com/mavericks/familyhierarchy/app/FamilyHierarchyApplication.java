package com.mavericks.familyhierarchy.app;

import com.mavericks.familyhierarchy.services.DefaultFamilyLoader;
import com.typesafe.config.Config;
import com.mavericks.familyhierarchy.io.IoOperations;
import com.mavericks.familyhierarchy.executors.CommandResolver;

import java.util.regex.Pattern;
import javax.inject.Inject;

public class FamilyHierarchyApplication {

  private final DefaultFamilyLoader defaultFamilyLoader;
  private final IoOperations ioOperations;
  private final CommandResolver commandResolver;
  private final Config config;

  @Inject
  FamilyHierarchyApplication(DefaultFamilyLoader defaultFamilyLoader,
                             IoOperations ioOperations,
                             CommandResolver commandResolver,
                             Config config) {
    this.defaultFamilyLoader = defaultFamilyLoader;
    this.ioOperations = ioOperations;
    this.commandResolver = commandResolver;
    this.config = config;
  }

  public void start() {
    if (config.getBoolean("load.default.family"))
      defaultFamilyLoader.load();

    ioOperations.println("Please enter a command to start operations (quit/exit/q to exit): ");
    final String exitPatternString = config.getString("patterns.exit");
    final Pattern exitPattern = Pattern.compile(exitPatternString, Pattern.CASE_INSENSITIVE);

    while (true) {
      final String request = ioOperations.getUserRequest();
      if (exitPattern.matcher(request).matches()) {
        break;
      }
      final String result = commandResolver.resolve(request);
      ioOperations.println(result);
    }
  }
}
