package com.mavericks.familyhierarchy.services.operations.impl;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mavericks.familyhierarchy.models.ESex;
import com.mavericks.familyhierarchy.models.Person;
import com.mavericks.familyhierarchy.services.operations.PersonAdder;
import com.mavericks.familyhierarchy.services.operations.PersonFetcher;
import com.mavericks.familyhierarchy.services.operations.PersonOperationsResolver;
import org.junit.After;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PersonOperationsResolverImplTest {

  private final PersonAdder mockPersonAdder = mock(PersonAdder.class);
  private final PersonFetcher mockPersonFetcher = mock(PersonFetcher.class);

  @After
  public void tearDown() {
    reset(mockPersonAdder, mockPersonFetcher);
  }

  @Test
  public void addMarriageShouldReturnAddedSpouse() {
    final Person testHusband = new Person("test-husband", ESex.MALE, null, null);
    final Person testWife = new Person("test-wife", ESex.FEMALE, null, null);
    final Optional<Person> expectedWife = Optional.of(testWife);
    final Map<String, Person> testFamilyMap = Collections.singletonMap("test-husband", testHusband);
    when(mockPersonAdder.addMarriage(anyString(), anyString(), any())).thenReturn(expectedWife);
    final PersonOperationsResolver personOperationsResolver = new PersonOperationsResolverImpl(mockPersonAdder, mockPersonFetcher);

    final Optional<Person> actualWife = personOperationsResolver.addMarriage("test-husband", "test-wife");

    verify(mockPersonAdder, times(1)).addMarriage("test-husband", "test-wife", testFamilyMap);
    assertEquals(expectedWife, actualWife);
  }

  @Test
  public void addChildShouldReturnAddedSon() {
    final Person testSon = new Person("test-son", ESex.MALE, null, null);
    final Person testMother = new Person("test-mother", ESex.FEMALE, null, null);
    final Map<String, Person> testFamilyMap = Collections.singletonMap("test-mother", testMother);
    final Optional<Person> expectedSon = Optional.of(testSon);
    when(mockPersonAdder.addChild(anyString(), anyString(), any(), any())).thenReturn(expectedSon);
    final PersonOperationsResolver personOperationsResolver = new PersonOperationsResolverImpl(mockPersonAdder, mockPersonFetcher);

    final Optional<Person> actualSon = personOperationsResolver.addChild("test-mother", "test-child", ESex.MALE);

    verify(mockPersonAdder, times(1)).addChild("test-mother", "test-child", ESex.MALE, testFamilyMap);
    assertEquals(expectedSon, actualSon);
  }

  @Test
  public void addChildShouldReturnAddedDaughter() {
    final Person testDaughter = new Person("test-daughter", ESex.FEMALE, null, null);
    final Person testMother = new Person("test-mother", ESex.FEMALE, null, null);
    final Map<String, Person> testFamilyMap = Collections.singletonMap("test-mother", testMother);
    final Optional<Person> expectedDaughter = Optional.of(testDaughter);
    when(mockPersonAdder.addChild(anyString(), anyString(), any(), any())).thenReturn(expectedDaughter);
    final PersonOperationsResolver personOperationsResolver = new PersonOperationsResolverImpl(mockPersonAdder, mockPersonFetcher);

    final Optional<Person> actualDaughter = personOperationsResolver.addChild("test-mother", "test-child", ESex.FEMALE);

    verify(mockPersonAdder, times(1)).addChild("test-mother", "test-child", ESex.FEMALE, testFamilyMap);
    assertEquals(expectedDaughter, actualDaughter);
  }

  @Test
  public void getChildrenShouldReturnAllChildren() {
    final Person testMother = new Person("test-mother", ESex.FEMALE, null, null);
    final Person testSon = new Person("test-son", ESex.MALE, null, testMother);
    final List<Person> expectedChildren = Collections.singletonList(testSon);
    when(mockPersonFetcher.getChildren(anyString(), any(), any())).thenReturn(expectedChildren);
    final PersonOperationsResolver personOperationsResolver = new PersonOperationsResolverImpl(mockPersonAdder, mockPersonFetcher);

    final List<Person> actualChildren = personOperationsResolver.getChildren("test-mother", ESex.MALE);

    verify(mockPersonFetcher, times(1)).getChildren("test-mother", ESex.MALE, Collections.emptyMap());
    assertThat(actualChildren, is(expectedChildren));
  }

  @Test
  public void getParentrShouldReturnOptionalParent() {
    final Person testFather = new Person("test-father", ESex.MALE, null, null);
    final Optional<Person> expectedFather = Optional.of(testFather);
    when(mockPersonFetcher.getParent(anyString(), any(), any())).thenReturn(expectedFather);
    final PersonOperationsResolver personOperationsResolver = new PersonOperationsResolverImpl(mockPersonAdder, mockPersonFetcher);

    final Optional<Person> actualFather = personOperationsResolver.getParent("test-son", ESex.MALE);

    verify(mockPersonFetcher, times(1)).getParent("test-son", ESex.MALE, Collections.emptyMap());
    assertThat(actualFather, is(expectedFather));
  }

  @Test
  public void getSiblingsShouldReturnAllSiblings() {
    final Person testSon = new Person("test-son1", ESex.MALE, null, null);
    List<Person> expectedSiblings = Collections.singletonList(testSon);
    when(mockPersonFetcher.getSiblings(anyString(), any(), any())).thenReturn(expectedSiblings);
    final PersonOperationsResolver personOperationsResolver = new PersonOperationsResolverImpl(mockPersonAdder, mockPersonFetcher);

    List<Person> actualSiblings = personOperationsResolver.getSiblings("test-son2", ESex.MALE);

    verify(mockPersonFetcher, times(1)).getSiblings("test-son2", ESex.MALE, Collections.emptyMap());
    assertThat(actualSiblings, is(expectedSiblings));
  }

  @Test
  public void getPartnerShouldReturnPartner() {
    final Person testFather = new Person("test-father", ESex.MALE, null, null);
    Optional<Person> expectedPartner = Optional.of(testFather);
    when(mockPersonFetcher.getPartner(anyString(), any(), any())).thenReturn(expectedPartner);
    final PersonOperationsResolver personOperationsResolver = new PersonOperationsResolverImpl(mockPersonAdder, mockPersonFetcher);

    Optional<Person> actualPartner = personOperationsResolver.getPartner("test-wife", ESex.MALE);

    verify(mockPersonFetcher, times(1)).getPartner("test-wife", ESex.MALE, Collections.emptyMap());
    assertThat(actualPartner, is(expectedPartner));
  }

  @Test
  public void getGrandParentShouldReturnGrandParent() {
    final Person testPerson = new Person("test-person", ESex.MALE, null, null);
    Optional<Person> expectedGrandMother = Optional.of(testPerson);
    when(mockPersonFetcher.getGrandParent(anyString(), any(), any())).thenReturn(expectedGrandMother);
    final PersonOperationsResolver personOperationsResolver = new PersonOperationsResolverImpl(mockPersonAdder, mockPersonFetcher);

    Optional<Person> actualGrandMother = personOperationsResolver.getGrandParent("test-grandchild", ESex.MALE);

    verify(mockPersonFetcher, times(1)).getGrandParent("test-grandchild", ESex.MALE, Collections.emptyMap());
    assertThat(actualGrandMother, is(expectedGrandMother));
  }
}
