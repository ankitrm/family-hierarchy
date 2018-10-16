package com.mavericks.familyhierarchy.services.operations;

import com.mavericks.familyhierarchy.models.ESex;
import com.mavericks.familyhierarchy.models.Person;

import java.util.Map;
import java.util.Optional;

public interface PersonAdder {
  Optional<Person> addMarriage(String husbandName, String wifeName, Map<String, Person> familyMap);

  Optional<Person> addChild(String motherName, String childName, ESex sex, Map<String, Person> familyMap);
}
