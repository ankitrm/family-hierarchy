package com.mavericks.familyhierarchy;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mavericks.familyhierarchy.app.FamilyHierarchyApplication;
import com.mavericks.familyhierarchy.injector.FamilyHierarchyModule;

public class Main {
  public static void main(String[] args) {
    Injector injector = Guice.createInjector(new FamilyHierarchyModule());
    FamilyHierarchyApplication application = injector.getInstance(FamilyHierarchyApplication.class);
    application.start();
  }
}
