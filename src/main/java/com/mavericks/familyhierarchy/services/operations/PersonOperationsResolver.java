package com.mavericks.familyhierarchy.services.operations;

import com.mavericks.familyhierarchy.models.ESex;
import com.mavericks.familyhierarchy.models.Person;

import java.util.List;
import java.util.Optional;

public interface PersonOperationsResolver {
  Optional<Person> addMarriage(String husband, String wife);

  List<Person> getChildren(String parentName, ESex sex);

  Optional<Person> getParent(String personName, ESex sex);

  List<Person> getSiblings(String personName, ESex sex);

  Optional<Person> getPartner(String person, ESex sexOfPartner);

  Optional<Person> addChild(String mother, String child, ESex sexOfNewBorn);

  Optional<Person> getGrandParent(String person, ESex sex);

  List<Person> getCousins(String person);

  List<Person> getAuntsOrUncles(String person, ESex auntOrUncleSex);

  List<Person> getGrandChildren(String person, ESex sex);
}
