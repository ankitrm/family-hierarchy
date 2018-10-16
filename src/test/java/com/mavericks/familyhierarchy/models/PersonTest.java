package com.mavericks.familyhierarchy.models;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PersonTest {

  @Test
  public void getNameShouldHaveCorrectName() {
    final String expectedPersonName = "test-name";
    final Person testPerson = new Person(expectedPersonName, ESex.MALE, null, null);

    final String actualPersonName = testPerson.getName();

    assertEquals(expectedPersonName, actualPersonName);
  }

  @Test
  public void getSexShouldHaveCorrectSex() {
    final ESex expectedPersonSex = ESex.MALE;
    final Person testPerson = new Person("test-name", expectedPersonSex, null, null);

    final ESex actualPersonSex = testPerson.getSex();

    assertEquals(expectedPersonSex, actualPersonSex);
  }

  @Test
  public void getSpouseShouldReturnEmpty() {
    final Optional<Person> expectedSpouse = Optional.empty();

    final Optional<Person> actualPersonSpouse = new Person("test-name", ESex.MALE, null, null).getSpouse();

    assertEquals(expectedSpouse, actualPersonSpouse);
  }

  @Test
  public void getSpouseShouldReturnSpouse() {
    final Person testFather = new Person("test-father-name", ESex.MALE, null, null);
    final Person testMother = new Person("test-mother-name", ESex.FEMALE, null, null);

    testFather.addSpouse(testMother);

    assertThat(testFather.getSpouse(), is(Optional.of(testMother)));
    assertThat(testMother.getSpouse(), is(Optional.of(testFather)));
  }

  @Test
  public void getFatherShouldReturnEmpty() {
    final Optional<Person> expectedFather = Optional.empty();
    final Person testPerson = new Person("test-name", ESex.MALE, null, null);

    final Optional<Person> actualFather = testPerson.getFather();

    assertThat(actualFather, is(expectedFather));
  }

  @Test
  public void getFatherShouldReturnFathersName() {
    final Person testFather = new Person("test-father-name", ESex.MALE, null, null);
    final Person testMother = new Person("test-mother-name", ESex.FEMALE, null, null);
    final Person testSon = new Person("test-son", ESex.MALE, testFather, testMother);

    final Optional<Person> actualOptFather = testSon.getFather();

    assertThat(actualOptFather, is(Optional.of(testFather)));
  }

  @Test
  public void getMotherShouldReturnEmpty() {
    final Person testMother = new Person("test-mother-name", ESex.FEMALE, null, null);
    final Person testSon = new Person("test-son", ESex.MALE, null, testMother);

    assertThat(testSon.getMother(), is(Optional.of(testMother)));
  }

  @Test
  public void addChildShouldBeAbleToAddNewChild() {
    final Person testFather = new Person("test-father-name", ESex.MALE, null, null);
    final Person testMother = new Person("test-mother-name", ESex.FEMALE, null, null);
    final Person testSon = new Person("test-son", ESex.MALE, testFather, testMother);
    testMother.addSpouse(testFather);
    testMother.addChild(testSon);

    assertEquals(testSon, testMother.getChildren().get(0));
    assertEquals(testSon, testFather.getChildren().get(0));
  }

  @Test
  public void getChildrenShouldBeAbleToFetchChild() {
    final Person testFather = new Person("test-father-name", ESex.MALE, null, null);
    final Person testMother = new Person("test-mother-name", ESex.FEMALE, null, null);
    final Person testSon = new Person("test-son", ESex.MALE, testFather, testMother);
    testMother.addSpouse(testFather);
    testMother.addChild(testSon);

    final Person actualFathersChild = testFather.getChildren().get(0);
    final Person actualMothersChild = testMother.getChildren().get(0);

    assertEquals(testSon, actualFathersChild);
    assertEquals(testSon, actualMothersChild);
  }

  @Test
  public void isMaleShouldBeTrueIfMale() {
    final Person testPerson = new Person("test-name", ESex.MALE, null, null);

    final boolean isMale = testPerson.isMale();

    assertTrue(isMale);
  }

  @Test
  public void isMaleShouldBeFalseIfFeMale() {
    final Person testPerson = new Person("test-name", ESex.FEMALE, null, null);

    final boolean isMale = testPerson.isMale();

    assertFalse(isMale);
  }

  @Test
  public void isFemaleShouldBeTrueIfFemale() {
    final Person testPerson = new Person("test-name", ESex.FEMALE, null, null);

    final boolean isFemale = testPerson.isFemale();

    assertTrue(isFemale);
  }

  @Test
  public void isFemaleShouldBeFalseIfMale() {
    final Person testPerson = new Person("test-name", ESex.MALE, null, null);

    final boolean isFemale = testPerson.isFemale();

    assertFalse(isFemale);
  }

  @Test
  public void isEqualsShouldCompareByName() {
    final Person testPerson = new Person("test-name", ESex.MALE, null, null);

    final boolean isEqual = testPerson.isEquals("test-name");

    assertTrue(isEqual);
  }

  @Test
  public void hasChildrenShouldBeTrueIfChilderPresent() {
    final Person testMother = new Person("test-mother-name", ESex.FEMALE, null, null);
    final Person testSon = new Person("test-son", ESex.MALE, null, testMother);

    testMother.addChild(testSon);

    assertTrue(testMother.hasChildren());
  }

  @Test
  public void hasChildrenShouldBeFalseIfChilderPresent() {
    final Person testPerson = new Person("test-name", ESex.MALE, null, null);

    final boolean hasChildren = testPerson.hasChildren();

    assertFalse(hasChildren);
  }

  @Test
  public void getSelfAndSpouseNamesShouldReturnBothNameAndSpouseName() {
    final Person testFather = new Person("test-father-name", ESex.MALE, null, null);
    final Person testMother = new Person("test-mother-name", ESex.FEMALE, null, null);
    testMother.addSpouse(testFather);
    final List<String> expectedResult = Arrays.asList("test-mother-name", "test-father-name");

    final List<String> actualResult = testFather.getSelfAndSpouseNames();

    assertThat(actualResult, is(expectedResult));
  }

  @Test
  public void getSelfAndSpouseNamesShouldReturnOnlySelfName() {
    final List<String> expectedResult = Collections.singletonList("test-name");
    final Person testPerson = new Person("test-name", ESex.MALE, null, null);

    final List<String> actualResult = testPerson.getSelfAndSpouseNames();

    assertThat(actualResult, is(expectedResult));
  }

  @Test
  public void equalsShouldBeTrueIfObjectsAreEqual() {
    final Person testMalePerson = new Person("test-name", ESex.MALE, null, null);
    final Person testPerson = new Person("test-name", ESex.MALE, null, null);

    assertEquals(testPerson, testMalePerson);
  }

  @Test
  public void equalsShouldBeFalseIfObjectsAreNotEqual() {
    final Person testFather = new Person("test-father-name", ESex.MALE, null, null);
    final Person testMother = new Person("test-mother-name", ESex.FEMALE, null, null);

    assertNotEquals(testMother, testFather);
  }

  @Test
  public void toStringShouldReturnExpectedString() {
    final Person testPerson = new Person("test-father-name", ESex.MALE, null, null);
    final String expectedString = "Person{name='test-father-name, sex=MALE, spouse=None, spouseSex=None, father=None, fatherSex=None, mother=None, motherSex=None, children=[]}";

    final String actualPersonString = testPerson.toString();

    assertEquals(expectedString, actualPersonString);
  }
}