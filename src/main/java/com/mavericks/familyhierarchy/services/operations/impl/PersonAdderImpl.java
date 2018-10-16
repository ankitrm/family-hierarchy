package com.mavericks.familyhierarchy.services.operations.impl;

import com.mavericks.familyhierarchy.models.ESex;
import com.mavericks.familyhierarchy.models.Person;
import com.mavericks.familyhierarchy.services.operations.PersonAdder;

import java.util.Map;
import java.util.Optional;

public class PersonAdderImpl implements PersonAdder {

  @Override
  public Optional<Person> addMarriage(final String husbandName, final String wifeName, Map<String, Person> familyMap) {
    final boolean isHusbandPresent = familyMap.containsKey(husbandName);
    final boolean isWifePresent = familyMap.containsKey(wifeName);
    if (isHusbandPresent && isWifePresent) {
      // Logger - cannot marry people in same family; logger - neither husband or wide exists
      return Optional.empty();
    }
    if (isHusbandPresent && familyMap.get(husbandName).getSex() == ESex.MALE) {
      final Person husband = familyMap.get(husbandName);
      final Person newWife = new Person(wifeName, ESex.FEMALE, null, null);
      husband.addSpouse(newWife);
      familyMap.put(wifeName, newWife);
      return Optional.of(newWife);
    }
    if (isWifePresent && familyMap.get(wifeName).getSex() == ESex.FEMALE) {
      final Person wife = familyMap.get(wifeName);
      final Person newHusband = new Person(husbandName, ESex.MALE, null, null);
      wife.addSpouse(newHusband);
      familyMap.put(husbandName, newHusband);
      return Optional.of(newHusband);
    }
    //neither husband or wife found
    return Optional.empty();
  }

  @Override
  public Optional<Person> addChild(final String motherName, final String childName, final ESex sex, Map<String, Person>
      familyMap) {

    final boolean doesMotherExist = familyMap.containsKey(motherName) && familyMap.get(motherName).getSex() == ESex.FEMALE;
    final boolean doesChildExist = familyMap.containsKey(childName);
    if (doesChildExist && doesMotherExist) {
      //logger -> both exist, operation not supported
      return Optional.empty();
    }
    if (doesMotherExist) {
      final Person mother = familyMap.get(motherName);
      final Person father = mother.getSpouse().orElse(null);
      final Person child = new Person(childName, sex, father, mother);
      familyMap.put(childName, child);
      mother.addChild(child);
      return Optional.of(child);
    }
    //neither mother or child exist
    return Optional.empty();
  }
}
