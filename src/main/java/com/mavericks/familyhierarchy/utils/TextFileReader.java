package com.mavericks.familyhierarchy.utils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TextFileReader implements FileReader {

  @Override
  public List<String> getAllExceptEmptyLines(String fileName) {
    try {
      final String filePath = Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).getFile();
      return Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8)
          .stream().filter(line -> !line.isEmpty()).collect(Collectors.toList());
    } catch (Exception fileReadException) {
      //LOGGER.warn("Failed to resolve default family heirarchy, please recheck the file: ")
      return Collections.emptyList();
    }
  }
}
