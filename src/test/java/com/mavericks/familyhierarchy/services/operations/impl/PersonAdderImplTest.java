package com.mavericks.familyhierarchy.services.operations.impl;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import com.google.common.collect.ImmutableMap;
import com.mavericks.familyhierarchy.models.ESex;
import com.mavericks.familyhierarchy.models.Person;
import com.mavericks.familyhierarchy.services.operations.PersonAdder;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PersonAdderImplTest {

  private final PersonAdder personAdder = new PersonAdderImpl();
  @Test
  public void addMarriageShouldAddAndReturnWifeIfHusbandFoundInFamily() {
    final Person testHusband = new Person("test-husband", ESex.MALE, null, null);
    Map<String, Person> familyMap = new HashMap<String, Person>() {{ put("test-husband", testHusband); }};

    final Optional<Person> actualWife = personAdder.addMarriage("test-husband", "test-wife", familyMap);

    assertEquals("test-husband", actualWife.get().getSpouse().get().getName());
  }

  @Test
  public void addMarriageShouldAddAndReturnHusbandIfWifeIsFoundInFamily() {
    final Person testWife = new Person("test-wife", ESex.FEMALE, null, null);
    Map<String, Person> familyMap = new HashMap<String, Person>() {{ put("test-wife", testWife); }};

    final Optional<Person> actualHusband = personAdder.addMarriage("test-husband", "test-wife", familyMap);

    assertEquals("test-wife", actualHusband.get().getSpouse().get().getName());
  }

  @Test
  public void addMarriageShouldReturnNoneIfNobodyInFamily() {
    final Person testWife = new Person("test-wife", ESex.FEMALE, null, null);
    final Map<String, Person> familyMap = Collections.singletonMap("test-wife", testWife);

    final Optional<Person> actualSpouse = personAdder.addMarriage("fake-husband", "fake-wife", familyMap);

    assertThat(actualSpouse, is(Optional.empty()));
  }

  @Test
  public void addMarriageShouldReturnNoneIfBothInFamily() {
    final Person testHusband = new Person("test-husband", ESex.MALE, null, null);
    final Person testWife = new Person("test-wife", ESex.FEMALE, null, null);
    final Map<String, Person> familyMap = ImmutableMap.of("test-husband", testHusband, "test-wife", testWife);

    final Optional<Person> actualSpouse = personAdder.addMarriage("test-husband", "test-wife", familyMap);

    assertThat(actualSpouse, is(Optional.empty()));
  }

  @Test
  public void addChildShouldAddChildToMother() {
    final Person testMother = new Person("test-mother", ESex.FEMALE, null, null);
    final Map<String, Person> familyMap = new HashMap<String, Person>() {{ put("test-mother", testMother); }};

    final Optional<Person> actualSon = personAdder.addChild("test-mother", "test-son", ESex.MALE, familyMap);

    assertEquals("test-mother", actualSon.get().getMother().get().getName());
  }

  @Test
  public void addChildShouldReturnEmptyIfBothMotherAndChildInFamily() {
    final Person testMother = new Person("test-mother", ESex.FEMALE, null, null);
    final Person testSon = new Person("test-son", ESex.MALE, null, null);
    final Map<String, Person> familyMap = ImmutableMap.of("test-mother", testMother, "test-son", testSon);

    final Optional<Person> actualSon = personAdder.addChild("test-mother", "test-son", ESex.MALE, familyMap);

    assertThat(actualSon, is(Optional.empty()));
  }

  @Test
  public void addChildShouldReturnEmptyIfMotherNotInFamily() {
    final Optional<Person> actualSon = personAdder.addChild("test-mother", "test-son", ESex.MALE, Collections.emptyMap());

    assertThat(actualSon, is(Optional.empty()));
  }
}