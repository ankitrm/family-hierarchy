package com.mavericks.familyhierarchy.generators;

import com.mavericks.familyhierarchy.models.Person;

import java.util.List;
import java.util.Optional;

public interface OutputGenerator {
  String getOutputForFetchRequest(String person, String relationName, List<Person> foundPeople);

  String getOutputForUnknownRequest(String inputRequest);

  String getOutputUnsupportedRelation(String relation);

  String getOutputForMarriageRequest(Optional<Person> newAdditionToFamily, String husband, String wife);

  String getOutputForNewBornRequest(Optional<Person> newAdditionToFamily, String motherName, String childName);
}
