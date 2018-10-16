package com.mavericks.familyhierarchy.io;

import java.util.List;
import java.util.Scanner;
import javax.inject.Inject;

public class ConsoleBasedIoOperations implements IoOperations {

  private Scanner scanner;

  @Inject
  public ConsoleBasedIoOperations(Scanner scanner) {
    this.scanner = scanner;
  }

  @Override
  public void printNoDefaultFamilyTree(String fileName) {
    System.out.println(
        String.format("Due to a system issue, we were unable to load the default family hierarchy from file '%s'", fileName)
    );
    System.out.println("List of supported operations to start a family:");
    System.out.println("'Husband=<HUSBAND_NAME> Wife=<WIFE_NAME>' or" +
        " 'Mother=<MOTHER_NAME> Son=<SON_NAME>' or" +
        " 'Mother=<MOTHER_NAME> Daughter=<DAUGHTER_NAME>'");
  }

  @Override
  public void println(List<String> results) {
    results.forEach(this::println);
  }

  @Override
  public String getUserRequest() {
    return scanner.nextLine();
  }

  @Override
  public void println(String result) {
    System.out.println(result);
  }
}
