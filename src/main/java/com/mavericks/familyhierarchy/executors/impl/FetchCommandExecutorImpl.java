package com.mavericks.familyhierarchy.executors.impl;

import static com.mavericks.familyhierarchy.patterns.FetchCommandPatterns.PATTERN_FETCH_PERSON_FROM_RELATION;

import com.mavericks.familyhierarchy.executors.FetchCommandExecutor;
import com.mavericks.familyhierarchy.generators.OutputGenerator;
import com.mavericks.familyhierarchy.models.EFetchRelationTypes;
import com.mavericks.familyhierarchy.models.ESex;
import com.mavericks.familyhierarchy.models.Person;
import com.mavericks.familyhierarchy.services.operations.PersonOperationsResolver;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import javax.inject.Inject;

public class FetchCommandExecutorImpl implements FetchCommandExecutor {

  private final PersonOperationsResolver personOperationsResolver;
  private final OutputGenerator outputGenerator;

  @Inject
  FetchCommandExecutorImpl(PersonOperationsResolver personOperationsResolver,
                           OutputGenerator outputGenerator) {
    this.personOperationsResolver = personOperationsResolver;
    this.outputGenerator = outputGenerator;
  }

  @Override
  public String execute(String command) {
    final Matcher fetchCommandMatcher = PATTERN_FETCH_PERSON_FROM_RELATION.matcher(command);
    if (fetchCommandMatcher.find()) {
      final String person = fetchCommandMatcher.group(1);
      final String relation = fetchCommandMatcher.group(2);
      return resolveFetchRequest(person, relation);
    } else {
      return outputGenerator.getOutputForUnknownRequest(command);
    }
  }

  private String resolveFetchRequest(String userInputPerson, String userInputRelation) {
    try {
      final String person = userInputPerson.toLowerCase();
      List<Person> foundPeople;
      final EFetchRelationTypes relationType = EFetchRelationTypes.valueOf(userInputRelation.toUpperCase());
      switch (relationType) {
        case HUSBAND:
          foundPeople = personOperationsResolver.getPartner(person, ESex.MALE).map(Collections::singletonList).orElseGet(Collections::emptyList);
          break;
        case WIFE:
          foundPeople = personOperationsResolver.getPartner(person, ESex.FEMALE).map(Collections::singletonList).orElseGet(Collections::emptyList);
          break;
        case FATHER:
          foundPeople = personOperationsResolver.getParent(person, ESex.MALE).map(Collections::singletonList).orElseGet(Collections::emptyList);
          break;
        case MOTHER:
          foundPeople = personOperationsResolver.getParent(person, ESex.FEMALE).map(Collections::singletonList).orElseGet(Collections::emptyList);
          break;
        case BROTHERS:
          foundPeople = personOperationsResolver.getSiblings(person, ESex.MALE);
          break;
        case BROTHER:
          foundPeople = getFirst(personOperationsResolver.getSiblings(person, ESex.MALE));
          break;
        case SISTERS:
          foundPeople = personOperationsResolver.getSiblings(person, ESex.FEMALE);
          break;
        case SISTER:
          foundPeople = getFirst(personOperationsResolver.getSiblings(person, ESex.FEMALE));
          break;
        case SONS:
          foundPeople = personOperationsResolver.getChildren(person, ESex.MALE);
          break;
        case SON:
          foundPeople = getFirst(personOperationsResolver.getChildren(person, ESex.MALE));
          break;
        case DAUGHTERS:
          foundPeople = personOperationsResolver.getChildren(person, ESex.FEMALE);
          break;
        case DAUGHTER:
          foundPeople = getFirst(personOperationsResolver.getChildren(person, ESex.FEMALE));
          break;
        case GRANDFATHER:
          foundPeople = personOperationsResolver.getGrandParent(person, ESex.MALE).map(Collections::singletonList).orElseGet(Collections::emptyList);
          break;
        case GRANDMOTHER:
          foundPeople = personOperationsResolver.getGrandParent(person, ESex.FEMALE).map(Collections::singletonList).orElseGet(Collections::emptyList);
          break;
        case COUSINS:
          foundPeople = personOperationsResolver.getCousins(person);
          break;
        case COUSIN:
          foundPeople = getFirst(personOperationsResolver.getCousins(person));
          break;
        case AUNTS:
          foundPeople = personOperationsResolver.getAuntsOrUncles(person, ESex.FEMALE);
          break;
        case AUNT:
          foundPeople = getFirst(personOperationsResolver.getAuntsOrUncles(person, ESex.FEMALE));
          break;
        case UNCLES:
          foundPeople = personOperationsResolver.getAuntsOrUncles(person, ESex.MALE);
          break;
        case UNCLE:
          foundPeople = getFirst(personOperationsResolver.getAuntsOrUncles(person, ESex.MALE));
          break;
        case GRANDSONS:
          foundPeople = personOperationsResolver.getGrandChildren(person, ESex.MALE);
          break;
        case GRANDSON:
          foundPeople = getFirst(personOperationsResolver.getGrandChildren(person, ESex.MALE));
          break;
        case GRANDDAUGHTERS:
          foundPeople = personOperationsResolver.getGrandChildren(person, ESex.FEMALE);
          break;
        case GRANDDAUGHTER:
          foundPeople = getFirst(personOperationsResolver.getGrandChildren(person, ESex.FEMALE));
          break;
        default:
          return outputGenerator.getOutputUnsupportedRelation(userInputRelation);
      }
      return outputGenerator.getOutputForFetchRequest(userInputPerson,
          EFetchRelationTypes.getDisplayableRelation(relationType, foundPeople.size() == 1),
          foundPeople);
    } catch (IllegalArgumentException illegalArgumentException) {
      //log the exception
      return outputGenerator.getOutputUnsupportedRelation(userInputRelation);
    }
  }

  private List<Person> getFirst(List<Person> persons) {
    return !persons.isEmpty() ? Collections.singletonList(persons.get(0)) : Collections.emptyList();
  }
}
