package com.mavericks.familyhierarchy.services.operations.impl;

import com.mavericks.familyhierarchy.models.ESex;
import com.mavericks.familyhierarchy.models.Person;
import com.mavericks.familyhierarchy.services.operations.PersonFetcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PersonFetcherImpl implements PersonFetcher {

  @Override
  public List<Person> getChildren(String parentName, ESex sex, Map<String, Person> familyMap) {
    if (familyMap.containsKey(parentName)) {
      return familyMap.get(parentName).getChildren().stream().filter(child -> child.getSex() == sex).collect(Collectors.toList());
    } else {
      return Collections.emptyList();
    }
  }

  @Override
  public Optional<Person> getParent(String personName, ESex sex, Map<String, Person> familyMap) {
    if (familyMap.containsKey(personName)) {
      if (sex == ESex.FEMALE)
        return familyMap.get(personName).getMother();
      else
        return familyMap.get(personName).getFather();
    } else {
      return Optional.empty();
    }
  }

  @Override
  public List<Person> getSiblings(String personName, ESex sex, Map<String, Person> familyMap) {
    if (familyMap.containsKey(personName)) {
      final Optional<Person> optMother = familyMap.get(personName).getMother();
      if (optMother.isPresent()) {
        return optMother.get()
            .getChildren()
            .stream()
            .filter(child -> !child.isEquals(personName) && child.getSex() == sex)
            .collect(Collectors.toList());
      }
    }
    return Collections.emptyList();
  }

  @Override
  public Optional<Person> getPartner(String personName, ESex sex, Map<String, Person> familyMap) {
    if (familyMap.containsKey(personName)) {
      final Optional<Person> optPartner = familyMap.get(personName).getSpouse();
      if (optPartner.isPresent() && optPartner.get().getSex() == sex) {
        return optPartner;
      }
    }
    return Optional.empty();
  }

  @Override
  public Optional<Person> getGrandParent(String person, ESex sex, Map<String, Person> familyMap) {
    Optional<Person> father = getParent(person, ESex.MALE, familyMap);
    if (father.isPresent()) {
      if (sex == ESex.MALE) {
        return father.get().getFather();
      } else {
        return father.get().getMother();
      }
    } else {
      Optional<Person> mother = getParent(person, ESex.FEMALE, familyMap);
      if (mother.isPresent()) {
        if (sex == ESex.MALE) {
          return mother.get().getFather();
        } else {
          return mother.get().getMother();
        }
      }
    }
    return Optional.empty();
  }

  @Override
  public List<Person> getCousins(String person, Map<String, Person> familyMap) {
    List<Person> aunts = getAuntsOrUncles(person, ESex.FEMALE, familyMap);
    List<Person> uncles = getAuntsOrUncles(person, ESex.MALE, familyMap);
    Stream<Person> uncleChildren = uncles.stream().flatMap(uncle -> uncle.getChildren().stream());
    Stream<Person> auntChildren = aunts.stream().flatMap(aunt -> aunt.getChildren().stream());
    return new ArrayList<>(Stream.concat(uncleChildren, auntChildren).collect(Collectors.toSet()));
  }

  private Stream<Person> getSpouseSiblings(String person, ESex siblingSex, Map<String, Person> familyMap) {
    return getSiblings(person, siblingSex, familyMap)
        .stream()
        .map(Person::getSpouse)
        .filter(Optional::isPresent)
        .map(Optional::get);
  }

  @Override
  public List<Person> getAuntsOrUncles(String person, ESex auntUncleSex, Map<String, Person> familyMap) {
    Optional<Person> mother = getParent(person, ESex.FEMALE, familyMap);
    Optional<Person> father = getParent(person, ESex.MALE, familyMap);
    if (mother.isPresent()) {
      String motherName = mother.get().getName();
      List<Person> motherSiblings = getSiblings(motherName, auntUncleSex, familyMap);
      List<Person> motherSiblingsSpouse = getSpouseSiblings(motherName, auntUncleSex, familyMap).collect(Collectors.toList());
      Stream<Person> maternalAuntsOrUncles = Stream.of(motherSiblings, motherSiblingsSpouse).flatMap(Collection::stream);
      if (father.isPresent()) {
        String fatherName = father.get().getName();
        List<Person> fatherSiblings = getSiblings(fatherName, auntUncleSex, familyMap);
        List<Person> fatherSiblingsSpouse = getSpouseSiblings(fatherName, ESex.getOppSex(auntUncleSex), familyMap).collect
            (Collectors.toList());
        Stream<Person> paternalAuntsOrUncles = Stream.of(fatherSiblings, fatherSiblingsSpouse).flatMap(Collection::stream);
        return Stream.concat(paternalAuntsOrUncles, maternalAuntsOrUncles).distinct().collect(Collectors.toList());
      }
      return maternalAuntsOrUncles.distinct().collect(Collectors.toList());
    }
    return Collections.emptyList();
  }

  @Override
  public List<Person> getGrandChildren(String grandparentName, ESex sex, Map<String, Person> familyMap) {
    List<Person> sons = getChildren(grandparentName, ESex.MALE, familyMap);
    List<Person> daughters = getChildren(grandparentName, ESex.FEMALE, familyMap);
    Stream<Person> children = Stream.concat(sons.stream(), daughters.stream());
    return children
        .flatMap(child ->
            child.getChildren().stream()
                .filter(grandChild -> grandChild.getSex() == sex))
        .collect(Collectors.toList());
  }
}
