package com.mavericks.familyhierarchy.injector;

import static org.junit.Assert.assertTrue;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mavericks.familyhierarchy.injector.FamilyHierarchyModule;
import com.mavericks.familyhierarchy.io.IoOperations;
import org.junit.Test;

public class FamilyHierarchyModuleTest {

  @Test
  public void configureShouldInitializeInstances() {
    final FamilyHierarchyModule instantiatedModule = new FamilyHierarchyModule();

    Injector testInjector = Guice.createInjector(instantiatedModule);

    assertTrue(testInjector.getInstance(IoOperations.class) != null);
  }
}