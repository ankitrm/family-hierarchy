package com.mavericks.familyhierarchy.services.operations.impl;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.google.common.collect.ImmutableMap;
import com.mavericks.familyhierarchy.models.ESex;
import com.mavericks.familyhierarchy.models.Person;
import com.mavericks.familyhierarchy.services.operations.PersonFetcher;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PersonFetcherImplTest {

  private final PersonFetcher personFetcher = new PersonFetcherImpl();

  @Test
  public void getChildrenShouldReturnAllPresentSons() {
    final Person testMother = new Person("test-mother", ESex.FEMALE, null, null);
    final Person testSon = new Person("test-son", ESex.MALE, null, testMother);
    testMother.addChild(testSon);
    final Map<String, Person> familyMap = ImmutableMap.of("test-mother", testMother, "test-son", testSon);
    final List<Person> expectedSons = Collections.singletonList(testSon);

    final List<Person> actualSons = personFetcher.getChildren("test-mother", ESex.MALE, familyMap);

    assertThat(actualSons, is(expectedSons));
  }

  @Test
  public void getChildrenShouldReturnNoneIfNoSonsArePresent() {
    final Person testMother = new Person("test-mother", ESex.FEMALE, null, null);
    final Map<String, Person> familyMap = Collections.singletonMap("test-mother", testMother);

    final List<Person> actualSon = personFetcher.getChildren("test-mother", ESex.MALE, familyMap);

    assertThat(actualSon, is(Collections.emptyList()));
  }

  @Test
  public void getChildrenShouldReturnAllPresentDaughters() {
    final Person testMother = new Person("test-mother", ESex.FEMALE, null, null);
    final Person testDaughter = new Person("test-daughter", ESex.FEMALE, null, testMother);
    testMother.addChild(testDaughter);
    final Map<String, Person> familyMap = ImmutableMap.of("test-mother", testMother, "test-daughter", testDaughter);
    final List<Person> expectedDaughters = Collections.singletonList(testDaughter);

    final List<Person> actualDaughters = personFetcher.getChildren("test-mother", ESex.FEMALE, familyMap);

    assertThat(actualDaughters, is(expectedDaughters));
  }

  @Test
  public void getChildrenShouldReturnNoneIfNoDaughtersArePresent() {
    final Person testMother = new Person("test-mother", ESex.FEMALE, null, null);
    final Map<String, Person> familyMap = Collections.singletonMap("test-mother", testMother);

    final List<Person> actualDaughter = personFetcher.getChildren("test-mother", ESex.FEMALE, familyMap);

    assertThat(actualDaughter, is(Collections.emptyList()));
  }

  @Test
  public void getChildrenShouldReturnNoneIfParentNotInFamily() {
    final Person testMother = new Person("test-mother", ESex.FEMALE, null, null);
    final Map<String, Person> familyMap = Collections.singletonMap("test-mother", testMother);

    List<Person> actualDaughters = personFetcher.getChildren("fake-child", ESex.FEMALE, familyMap);

    assertThat(actualDaughters, is(Collections.emptyList()));
  }

  @Test
  public void getParentShouldReturnFatherIfPresent() {
    final Person testFather = new Person("test-father", ESex.MALE, null, null);
    final Person testDaughter = new Person("test-daughter", ESex.FEMALE, testFather, null);
    testFather.addChild(testDaughter);
    final Map<String, Person> familyMap = ImmutableMap.of("test-father", testFather, "test-daughter", testDaughter);

    Optional<Person> actualOptMother = personFetcher.getParent("test-daughter", ESex.MALE, familyMap);

    assertThat(actualOptMother, is(Optional.of(testFather)));
  }

  @Test
  public void getParentShouldReturnNoneIfParentNotPresent() {
    final Person testFather = new Person("test-father", ESex.FEMALE, null, null);
    final Map<String, Person> familyMap = Collections.singletonMap("test-father", testFather);

    Optional<Person> actualOptFather = personFetcher.getParent("fake-parent-name", ESex.MALE, familyMap);

    assertThat(actualOptFather, is(Optional.empty()));
  }

  @Test
  public void getParentShouldReturnMothersIfPresent() {
    final Person testMother = new Person("test-mother", ESex.FEMALE, null, null);
    final Person testDaughter = new Person("test-daughter", ESex.FEMALE, null, testMother);
    testMother.addChild(testDaughter);
    final Map<String, Person> familyMap = ImmutableMap.of("test-mother", testMother, "test-daughter", testDaughter);

    Optional<Person> actualOptMother = personFetcher.getParent("test-daughter", ESex.FEMALE, familyMap);

    assertThat(actualOptMother, is(Optional.of(testMother)));
  }

  @Test
  public void getSiblingsShouldReturnAllBrothersIfPresent() {
    final Person testMother = new Person("test-mother", ESex.FEMALE, null, null);
    final Person testSon1 = new Person("test-son1", ESex.MALE, null, testMother);
    final Person testSon2 = new Person("test-son2", ESex.MALE, null, testMother);
    Arrays.asList(testSon1, testSon2).forEach(testMother::addChild);
    final Map<String, Person> familyMap = ImmutableMap.of("test-mother", testMother, "test-son1", testSon1, "test-son2",
        testSon2);
    final List<Person> expectedBrothers = Collections.singletonList(testSon1);

    List<Person> actualBrothers = personFetcher.getSiblings("test-son2", ESex.MALE, familyMap);

    assertThat(actualBrothers, is(expectedBrothers));
  }

  @Test
  public void getSiblingsShouldReturnNoneIfNoBrothersPresent() {
    final Person testMother = new Person("test-mother", ESex.FEMALE, null, null);
    final Person testSon = new Person("test-son", ESex.MALE, null, testMother);
    testMother.addChild(testSon);
    final Map<String, Person> familyMap = ImmutableMap.of("test-mother", testMother, "test-son", testSon);

    List<Person> actualBrothers = personFetcher.getSiblings("test-son", ESex.MALE, familyMap);

    assertThat(actualBrothers, is(Collections.emptyList()));
  }

  @Test
  public void getSiblingsShouldReturnNoneIfParentIsNotPresent() {
    final Person testMother = new Person("test-mother", ESex.FEMALE, null, null);
    final Map<String, Person> familyMap = ImmutableMap.of("test-mother", testMother);

    List<Person> actualBrothers = personFetcher.getSiblings("fake-parent", ESex.MALE, familyMap);

    assertThat(actualBrothers, is(Collections.emptyList()));
  }


  @Test
  public void getSiblingsShouldReturnAllSistersIfPresent() {
    final Person testMother = new Person("test-mother", ESex.FEMALE, null, null);
    final Person testDaughter1 = new Person("test-daughter1", ESex.FEMALE, null, testMother);
    final Person testDaughter2 = new Person("test-daughter2", ESex.FEMALE, null, testMother);
    Arrays.asList(testDaughter1, testDaughter2).forEach(testMother::addChild);
    final Map<String, Person> familyMap = ImmutableMap.of("test-mother", testMother, "test-daughter1", testDaughter1,
        "test-daughter2", testDaughter2);
    final List<Person> expectedSisters = Collections.singletonList(testDaughter1);

    List<Person> actualSisters = personFetcher.getSiblings("test-daughter2", ESex.FEMALE, familyMap);

    assertThat(actualSisters, is(expectedSisters));
  }

  @Test
  public void getSiblingsShouldReturnNoneIfNoSistersPresent() {
    final Person testMother = new Person("test-mother", ESex.FEMALE, null, null);
    final Person testDaughter = new Person("test-daughter", ESex.FEMALE, null, testMother);
    testMother.addChild(testDaughter);
    final Map<String, Person> familyMap = ImmutableMap.of("test-mother", testMother, "test-daughter", testDaughter);

    List<Person> actualSisters = personFetcher.getSiblings("test-daughter", ESex.FEMALE, familyMap);

    assertThat(actualSisters, is(Collections.emptyList()));
  }

  @Test
  public void getPartnerShouldReturnHusbandIfPresent() {
    final Person expectedHusband = new Person("test-husband", ESex.MALE, null, null);
    final Person testWife = new Person("test-wife", ESex.FEMALE, null, null);
    expectedHusband.addSpouse(testWife);
    final Map<String, Person> familyMap = ImmutableMap.of("test-husband", expectedHusband, "test-wife", testWife);

    Optional<Person> actualHusband = personFetcher.getPartner("test-wife", ESex.MALE, familyMap);

    assertThat(actualHusband, is(Optional.of(expectedHusband)));
  }

  @Test
  public void getPartnerShouldReturnWifeIsPresent() {
    final Person testHusband = new Person("test-husband", ESex.MALE, null, null);
    final Person expectedWife = new Person("test-wife", ESex.FEMALE, null, null);
    testHusband.addSpouse(expectedWife);
    final Map<String, Person> familyMap = ImmutableMap.of("test-husband", testHusband, "test-wife", expectedWife);

    Optional<Person> actualWife = personFetcher.getPartner("test-husband", ESex.FEMALE, familyMap);

    assertThat(actualWife, is(Optional.of(expectedWife)));
  }

  @Test
  public void getGrandParentShouldReturnPaternalGrandFatherWhenRequested() {
    final Person expectedGrandFather = new Person("test-grandfather", ESex.MALE, null, null);
    final Person testFather = new Person("test-father", ESex.MALE, expectedGrandFather, null);
    final Person testPerson = new Person("test-person", ESex.MALE, testFather, null);
    final Map<String, Person> familyMap = ImmutableMap.of("test-person", testPerson, "test-father", testFather, "test-grandfather", expectedGrandFather);

    Optional<Person> actualGrandfather = personFetcher.getGrandParent("test-person", ESex.MALE, familyMap);

    assertThat(actualGrandfather, is(Optional.of(expectedGrandFather)));
  }

  @Test
  public void getGrandParentShouldReturnPaternalGrandMotherWhenRequested() {
    final Person expectedGrandMother = new Person("test-grandmother", ESex.FEMALE, null, null);
    final Person testFather = new Person("test-father", ESex.MALE, null, expectedGrandMother);
    final Person testPerson = new Person("test-person", ESex.MALE, testFather, null);
    final Map<String, Person> familyMap = ImmutableMap.of("test-person", testPerson, "test-father", testFather, "test-grandmother", expectedGrandMother);

    Optional<Person> actualGrandMother = personFetcher.getGrandParent("test-person", ESex.FEMALE, familyMap);

    assertThat(actualGrandMother, is(Optional.of(expectedGrandMother)));
  }

  @Test
  public void getGrandParentShouldReturnMaternalGrandFatherWhenRequested() {
    final Person expectedGrandFather = new Person("test-grandfather", ESex.MALE, null, null);
    final Person testMother = new Person("test-mother", ESex.MALE, expectedGrandFather, null);
    final Person testPerson = new Person("test-person", ESex.MALE, null, testMother);
    final Map<String, Person> familyMap = ImmutableMap.of("test-person", testPerson, "test-mother", testMother, "test-grandfather", expectedGrandFather);

    Optional<Person> actualGrandfather = personFetcher.getGrandParent("test-person", ESex.MALE, familyMap);

    assertThat(actualGrandfather, is(Optional.of(expectedGrandFather)));
  }

  @Test
  public void getGrandParentShouldReturnMaternalGrandMotherWhenRequested() {
    final Person expectedGrandMother = new Person("test-grandmother", ESex.FEMALE, null, null);
    final Person testMother = new Person("test-mother", ESex.MALE, null, expectedGrandMother);
    final Person testPerson = new Person("test-person", ESex.MALE, null, testMother);
    final Map<String, Person> familyMap = ImmutableMap.of("test-person", testPerson, "test-mother", testMother, "test-grandmother", expectedGrandMother);

    Optional<Person> actualGrandMother = personFetcher.getGrandParent("test-person", ESex.FEMALE, familyMap);

    assertThat(actualGrandMother, is(Optional.of(expectedGrandMother)));
  }

  @Test
  public void getGrandParentShouldReturnNoneIfPersonDoesnotExist() {
    final Person testPerson = new Person("test-person", ESex.MALE, null, null);
    final Map<String, Person> familyMap = ImmutableMap.of("test-person", testPerson);

    Optional<Person> actualGrandMother = personFetcher.getGrandParent("fake-person", ESex.FEMALE, familyMap);

    assertThat(actualGrandMother, is(Optional.empty()));
  }

  @Test
  public void getPartnerShouldReturnNonIfNoPartnerFound() {
    final Person testPerson = new Person("test-person", ESex.MALE, null, null);
    final Map<String, Person> familyMap = ImmutableMap.of("test-person", testPerson);

    Optional<Person> actualPartner = personFetcher.getPartner("fakeperson", ESex.MALE, familyMap);

    assertThat(actualPartner, is(Optional.empty()));
  }

  @Test
  public void getGrandchildrenShouldReturnGrandSonWhenPresent() {
    final Person testGrandMother = new Person("test-grandmother", ESex.FEMALE, null, null);
    final Person testMother = new Person("test-mother", ESex.FEMALE, null, testGrandMother);
    final Person testPerson = new Person("test-person", ESex.MALE, null, testMother);
    testGrandMother.addChild(testMother);
    testMother.addChild(testPerson);
    final Map<String, Person> familyMap = ImmutableMap.of("test-person", testPerson, "test-mother", testMother, "test-grandmother", testGrandMother);

    List<Person> actualGrandchildren = personFetcher.getGrandChildren("test-grandmother", ESex.MALE, familyMap);

    assertThat(actualGrandchildren, is(Collections.singletonList(testPerson)));
  }

  @Test
  public void getAuntsOrUnclesShouldReturnAuntOrUncle() {
    final Person testGrandMother = new Person("test-grandmother", ESex.FEMALE, null, null);
    final Person testMother = new Person("test-mother", ESex.FEMALE, null, testGrandMother);
    final Person testFather = new Person("test-father", ESex.MALE, null, testGrandMother);
    final Person testFathersSibling = new Person("test-father-sibling", ESex.FEMALE, null, testGrandMother);
    final Person testChild = new Person("test-person", ESex.FEMALE, testFather, testMother);
    testFather.addSpouse(testMother);
    testGrandMother.addChild(testFather);
    testGrandMother.addChild(testFathersSibling);
    testFather.addChild(testChild);
    final Map<String, Person> familyMap = ImmutableMap.of("test-father-sibling", testFathersSibling, "test-person", testChild, "test-father", testFather, "test-grandmother", testGrandMother);

    List<Person> actualAuntUncles = personFetcher.getAuntsOrUncles("test-person", ESex.FEMALE, familyMap);

    assertThat(actualAuntUncles, is(Collections.singletonList(testFathersSibling)));
  }

  @Test
  public void getAuntsOrUnclesShouldReturnNoneIfInvalidPerson() {
    final Person testPerson = new Person("test-person", ESex.MALE, null, null);
    final Map<String, Person> familyMap = ImmutableMap.of("test-person", testPerson);

    List<Person> actualAuntOrUncle = personFetcher.getAuntsOrUncles("fakeperson", ESex.MALE, familyMap);

    assertThat(actualAuntOrUncle, is(Collections.emptyList()));
  }

  @Test
  public void getCousinsShouldReturnCousins() {
    final Person testGrandMother = new Person("test-grandmother", ESex.FEMALE, null, null);
    final Person testMother = new Person("test-mother", ESex.FEMALE, null, testGrandMother);
    final Person testMothersSibling = new Person("test-mother-sibling", ESex.FEMALE, null, testGrandMother);
    final Person testCousin = new Person("test-cousin", ESex.FEMALE, null, testMothersSibling);
    final Person testChild = new Person("test-person", ESex.FEMALE, null, testMother);
    testGrandMother.addChild(testMother);
    testGrandMother.addChild(testMothersSibling);
    testMothersSibling.addChild(testCousin);
    testMother.addChild(testChild);
    final Map<String, Person> familyMap = ImmutableMap.of("test-cousin", testCousin, "test-mother-sibling", testMothersSibling, "test-person", testChild, "test-mother", testMother, "test-grandmother", testGrandMother);

    List<Person> actualCousins = personFetcher.getCousins("test-person", familyMap);

    assertThat(actualCousins, is(Collections.singletonList(testCousin)));
  }
}