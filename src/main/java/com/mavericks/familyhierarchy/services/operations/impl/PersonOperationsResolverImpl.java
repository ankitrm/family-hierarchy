package com.mavericks.familyhierarchy.services.operations.impl;

import com.mavericks.familyhierarchy.models.ESex;
import com.mavericks.familyhierarchy.models.Person;
import com.mavericks.familyhierarchy.services.operations.PersonOperationsResolver;
import com.mavericks.familyhierarchy.services.operations.PersonAdder;
import com.mavericks.familyhierarchy.services.operations.PersonFetcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PersonOperationsResolverImpl implements PersonOperationsResolver {

  private Map<String, Person> familyMap = new HashMap<>();
  private final PersonAdder personAdder;
  private final PersonFetcher personFetcher;

  @Inject
  PersonOperationsResolverImpl(PersonAdder personAdder, PersonFetcher personFetcher) {
    this.personAdder = personAdder;
    this.personFetcher = personFetcher;
  }

  @Override
  public Optional<Person> addMarriage(final String husbandName, final String wifeName) {
    if (familyMap.isEmpty()) {
      Person husband = new Person(husbandName, ESex.MALE, null, null);
      familyMap.put(husbandName, husband);
    }
    return personAdder.addMarriage(husbandName, wifeName, familyMap);
  }

  @Override
  public Optional<Person> addChild(String motherName, String childName, ESex sex) {
    if (familyMap.isEmpty()) {
      Person mother = new Person(motherName, ESex.FEMALE, null, null);
      familyMap.put(motherName, mother);
    }
    return personAdder.addChild(motherName, childName, sex, familyMap);
  }

  @Override
  public Optional<Person> getGrandParent(String person, ESex sex) {
    return personFetcher.getGrandParent(person, sex, familyMap);
  }

  @Override
  public List<Person> getCousins(String person) {
    return personFetcher.getCousins(person, familyMap);
  }

  @Override
  public List<Person> getAuntsOrUncles(String person, ESex auntOrUncleSex) {
    return personFetcher.getAuntsOrUncles(person, auntOrUncleSex, familyMap);
  }

  @Override
  public List<Person> getGrandChildren(String grandparentName, ESex sex) {
    return personFetcher.getGrandChildren(grandparentName, sex, familyMap);
  }

  @Override
  public List<Person> getChildren(final String parentName, ESex sex) {
    return personFetcher.getChildren(parentName, sex, familyMap);
  }

  @Override
  public Optional<Person> getParent(String personName, ESex sex) {
    return personFetcher.getParent(personName, sex, familyMap);
  }

  @Override
  public List<Person> getSiblings(String personName, ESex sex) {
    return personFetcher.getSiblings(personName, sex, familyMap);
  }

  @Override
  public Optional<Person> getPartner(String personName, ESex sexOfPartner) {
    return personFetcher.getPartner(personName, sexOfPartner, familyMap);
  }
}
