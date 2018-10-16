package com.mavericks.familyhierarchy.injector;

import com.google.inject.AbstractModule;
import com.mavericks.familyhierarchy.executors.AddCommandExecutor;
import com.mavericks.familyhierarchy.executors.FetchCommandExecutor;
import com.mavericks.familyhierarchy.executors.impl.AddCommandExecutorImpl;
import com.mavericks.familyhierarchy.executors.impl.FetchCommandExecutorImpl;
import com.mavericks.familyhierarchy.services.DefaultFamilyLoader;
import com.mavericks.familyhierarchy.services.FileBasedDefaultFamilyLoader;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.mavericks.familyhierarchy.io.ConsoleBasedIoOperations;
import com.mavericks.familyhierarchy.io.IoOperations;
import com.mavericks.familyhierarchy.executors.CommandResolver;
import com.mavericks.familyhierarchy.executors.impl.CommandResolverImpl;
import com.mavericks.familyhierarchy.generators.OutputGenerator;
import com.mavericks.familyhierarchy.generators.OutputGeneratorImpl;
import com.mavericks.familyhierarchy.utils.FileReader;
import com.mavericks.familyhierarchy.utils.TextFileReader;
import com.mavericks.familyhierarchy.services.operations.PersonOperationsResolver;
import com.mavericks.familyhierarchy.services.operations.PersonAdder;
import com.mavericks.familyhierarchy.services.operations.PersonFetcher;
import com.mavericks.familyhierarchy.services.operations.impl.PersonOperationsResolverImpl;
import com.mavericks.familyhierarchy.services.operations.impl.PersonAdderImpl;
import com.mavericks.familyhierarchy.services.operations.impl.PersonFetcherImpl;

import java.util.Scanner;

public class FamilyHierarchyModule extends AbstractModule {

  @Override
  protected void configure() {
    bindConfig();
    bindExecutors();
    bindUtils();
    bindServices();
    bindIOs();
    bindGenerators();
  }

  private void bindGenerators() {
    bind(OutputGenerator.class).to(OutputGeneratorImpl.class);
  }

  private void bindIOs() {
    bind(Scanner.class).toInstance(new Scanner(System.in));
    bind(IoOperations.class).to(ConsoleBasedIoOperations.class);
  }

  private void bindUtils() {
    bind(FileReader.class).to(TextFileReader.class);
  }

  private void bindExecutors() {
    bind(AddCommandExecutor.class).to(AddCommandExecutorImpl.class);
    bind(FetchCommandExecutor.class).to(FetchCommandExecutorImpl.class);
    bind(CommandResolver.class).to(CommandResolverImpl.class);
  }

  private void bindConfig() {
    bind(Config.class).toInstance(ConfigFactory.load());
  }

  private void bindServices() {
    bind(DefaultFamilyLoader.class).to(FileBasedDefaultFamilyLoader.class);

    bind(PersonAdder.class).to(PersonAdderImpl.class);
    bind(PersonFetcher.class).to(PersonFetcherImpl.class);
    bind(PersonOperationsResolver.class).to(PersonOperationsResolverImpl.class).asEagerSingleton();
  }
}
