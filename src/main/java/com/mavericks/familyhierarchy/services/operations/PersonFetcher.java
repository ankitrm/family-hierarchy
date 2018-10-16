package com.mavericks.familyhierarchy.services.operations;

import com.mavericks.familyhierarchy.models.ESex;
import com.mavericks.familyhierarchy.models.Person;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PersonFetcher {

  List<Person> getChildren(String parentName, ESex sex, Map<String, Person> familyMap);

  List<Person> getSiblings(String personName, ESex sex, Map<String, Person> familyMap);

  Optional<Person> getPartner(String personName, ESex sex, Map<String, Person> familyMap);

  Optional<Person> getGrandParent(String person, ESex sex, Map<String, Person> familyMap);

  List<Person> getCousins(String person, Map<String, Person> familyMap);

  List<Person> getAuntsOrUncles(String person, ESex auntUncleSex, Map<String, Person> familyMap);

  List<Person> getGrandChildren(String grandparentName, ESex sex, Map<String, Person> familyMap);

  Optional<Person> getParent(String personName, ESex sex, Map<String, Person> familyMap);

}
