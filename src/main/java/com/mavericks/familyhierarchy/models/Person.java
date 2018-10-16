package com.mavericks.familyhierarchy.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class Person {

  private String name;
  private ESex sex;
  private Person spouse;
  private Person father;
  private Person mother;

  private List<Person> children = new ArrayList<>();

  public Person(String name, ESex sex, Person father, Person mother) {
    this.name = name;
    this.sex = sex;
    this.father = father;
    this.mother = mother;
  }

  public String getName() {
    return name;
  }

  public ESex getSex() {
    return sex;
  }

  public Optional<Person> getSpouse() {
    return Optional.ofNullable(spouse);
  }

  public Optional<Person> getFather() {
    return Optional.ofNullable(father);
  }

  public Optional<Person> getMother() {
    return Optional.ofNullable(mother);
  }

  public void addSpouse(Person spouse) {
    spouse.spouse = this;
    this.spouse = spouse;
  }

  public void addChild(Person child) {
    if (getSpouse().isPresent()) {
      getSpouse().get().children.add(child);
    }
    children.add(child);
  }


  public List<Person> getChildren() {
    return children;
  }

  public boolean isMale() {
    return sex == ESex.MALE;
  }

  public boolean isFemale() {
    return sex == ESex.FEMALE;
  }

  public boolean isEquals(String name) {
    return name.equalsIgnoreCase(this.getName());
  }

  public boolean hasChildren() {
    return !children.isEmpty();
  }

  public List<String> getSelfAndSpouseNames() {
    if (getSpouse().isPresent()) {
      return Arrays.asList(getSpouse().get().getName(), getName());
    } else {
      return Collections.singletonList(name);
    }
  }

  public String getDisplayableName() {
    return name.substring(0, 1).toUpperCase() +
        name.substring(1, name.length()).toLowerCase();
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Person)) return false;
    Person person = (Person) o;
    return Objects.equals(getName(), person.getName()) &&
        getSex() == person.getSex() &&
        Objects.equals(getSpouse().orElse(null), person.getSpouse().orElse(null)) &&
        Objects.equals(getFather().orElse(null), person.getFather().orElse(null)) &&
        Objects.equals(getMother().orElse(null), person.getMother().orElse(null)) &&
        Objects.equals(getChildren(), person.getChildren());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getSex(),
        getSpouse().isPresent()? getSpouse().get().getName(): null,
        getFather().isPresent()? getFather().get().getName():null,
        getMother().isPresent()? getMother().get().getName():null,
        getChildren());
  }

  @Override
  public String toString() {
    return "Person{" +
        "name='" + name +
        ", sex=" + sex +
        ", spouse=" + (spouse == null ? "None" : spouse.name) +
        ", spouseSex=" + (spouse == null ? "None" : spouse.sex) +
        ", father=" + (father == null ? "None" : father.name) +
        ", fatherSex=" + (father == null ? "None" : father.sex) +
        ", mother=" + (mother == null ? "None" : mother.name) +
        ", motherSex=" + (mother == null ? "None" : mother.sex) +
        ", children=[" + children.stream().map(child -> "{name=" + child.name+", sex="+child.sex+"}").collect(Collectors.joining(", ")) +
        "]}";
  }
}
