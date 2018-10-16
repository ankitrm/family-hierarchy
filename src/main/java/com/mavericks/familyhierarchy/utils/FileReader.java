package com.mavericks.familyhierarchy.utils;

import java.util.List;

public interface FileReader {
  List<String> getAllExceptEmptyLines(String fileName);
}
