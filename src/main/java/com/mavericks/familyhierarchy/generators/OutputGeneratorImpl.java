package com.mavericks.familyhierarchy.generators;

import com.mavericks.familyhierarchy.models.Person;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OutputGeneratorImpl implements OutputGenerator {

  private static final String WELCOME_MESSAGE = "Welcome to family, %s!";

  @Override
  public String getOutputForFetchRequest(String person, String relationName, List<Person> foundPeople) {
    if (foundPeople.isEmpty()) {
      return String.format("Please check person `%s` or relation `%s`", person, relationName);
    } else
      return String.format("%s=%s",
          relationName,
          foundPeople.stream().map(Person::getDisplayableName).collect(Collectors.joining(",")));
  }

  @Override
  public String getOutputForUnknownRequest(String inputRequest) {
    return String.format("Please check the request again: %s", inputRequest);
  }

  @Override
  public String getOutputUnsupportedRelation(String relation) {
    return String.format("We do not support this '%s' relation right now", relation);
  }

  @Override
  public String getOutputForMarriageRequest(Optional<Person> newAdditionToFamily, String husband, String wife) {
    if (!newAdditionToFamily.isPresent()) {
      return String.format("Either members (%s, %s) already exist or do not exist family to marry", husband, wife);
    } else {
      return String.format(WELCOME_MESSAGE, newAdditionToFamily.get().getDisplayableName());
    }
  }

  @Override
  public String getOutputForNewBornRequest(Optional<Person> newAdditionToFamily, String motherName, String childName) {
    if (!newAdditionToFamily.isPresent()) {
      return String.format("We could not find Mother (%s) in family to add (%s)", motherName, childName);
    } else {
      return String.format(WELCOME_MESSAGE, newAdditionToFamily.get().getDisplayableName());
    }
  }
}
