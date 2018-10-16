package com.mavericks.familyhierarchy.io;

import java.util.List;

public interface IoOperations {
  void printNoDefaultFamilyTree(String fileName);

  void println(List<String> results);

  String getUserRequest();

  void println(String result);
}
