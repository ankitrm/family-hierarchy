package com.mavericks.familyhierarchy.executors.impl;

import static com.mavericks.familyhierarchy.patterns.AddCommandPatterns.PATTERN_INSERT_DAUGHTER_MOTHER;
import static com.mavericks.familyhierarchy.patterns.AddCommandPatterns.PATTERN_INSERT_HUSBAND_WIFE;
import static com.mavericks.familyhierarchy.patterns.AddCommandPatterns.PATTERN_INSERT_MOTHER_DAUGHTER;
import static com.mavericks.familyhierarchy.patterns.AddCommandPatterns.PATTERN_INSERT_MOTHER_SON;
import static com.mavericks.familyhierarchy.patterns.AddCommandPatterns.PATTERN_INSERT_PERSON_SPOUSE;
import static com.mavericks.familyhierarchy.patterns.AddCommandPatterns.PATTERN_INSERT_SON_MOTHER;
import static com.mavericks.familyhierarchy.patterns.AddCommandPatterns.PATTERN_INSERT_WIFE_HUSBAND;

import com.mavericks.familyhierarchy.executors.AddCommandExecutor;
import com.mavericks.familyhierarchy.generators.OutputGenerator;
import com.mavericks.familyhierarchy.models.ESex;
import com.mavericks.familyhierarchy.models.Person;
import com.mavericks.familyhierarchy.services.operations.PersonOperationsResolver;

import java.util.Optional;
import java.util.regex.Matcher;
import javax.inject.Inject;

public class AddCommandExecutorImpl implements AddCommandExecutor {

  private final PersonOperationsResolver personOperationsResolver;
  private final OutputGenerator outputGenerator;

  @Inject
  AddCommandExecutorImpl(PersonOperationsResolver personOperationsResolver,
                         OutputGenerator outputGenerator) {
    this.personOperationsResolver = personOperationsResolver;
    this.outputGenerator = outputGenerator;
  }

  @Override
  public String execute(String command) {
    final Matcher husbandWifeMatcher = PATTERN_INSERT_HUSBAND_WIFE.matcher(command);
    final Matcher wifeHusbandMatcher = PATTERN_INSERT_WIFE_HUSBAND.matcher(command);
    final Matcher motherSonMatcher = PATTERN_INSERT_MOTHER_SON.matcher(command);
    final Matcher sonMotherMatcher = PATTERN_INSERT_SON_MOTHER.matcher(command);
    final Matcher motherDaughterMatcher = PATTERN_INSERT_MOTHER_DAUGHTER.matcher(command);
    final Matcher daughterMotherMatcher = PATTERN_INSERT_DAUGHTER_MOTHER.matcher(command);
    final Matcher personSpouseMatcher = PATTERN_INSERT_PERSON_SPOUSE.matcher(command);
    //
    if (husbandWifeMatcher.find()) {
      return executeMarriage(husbandWifeMatcher.group(1), husbandWifeMatcher.group(2));
    } else if (wifeHusbandMatcher.find()) {
      return executeMarriage(wifeHusbandMatcher.group(2), wifeHusbandMatcher.group(1));
    } else if (motherSonMatcher.find()) {
      return executeNewBorn(motherSonMatcher.group(1), motherSonMatcher.group(2), ESex.MALE);
    } else if (sonMotherMatcher.find()) {
      return executeNewBorn(sonMotherMatcher.group(2), sonMotherMatcher.group(1), ESex.MALE);
    } else if (motherDaughterMatcher.find()) {
      return executeNewBorn(motherDaughterMatcher.group(1), motherDaughterMatcher.group(2), ESex.FEMALE);
    } else if (daughterMotherMatcher.find()) {
      return executeNewBorn(daughterMotherMatcher.group(2), daughterMotherMatcher.group(1), ESex.FEMALE);
    } else if (personSpouseMatcher.find()){
      return executeMarriage(personSpouseMatcher.group(1),personSpouseMatcher.group(2) );
    } else {
      return outputGenerator.getOutputForUnknownRequest(command);
    }
  }

  private String executeMarriage(String husband, String wife) {
    Optional<Person> newAdditionToFamily = personOperationsResolver.addMarriage(husband.toLowerCase(), wife.toLowerCase());
    return outputGenerator.getOutputForMarriageRequest(newAdditionToFamily, husband, wife);
  }

  private String executeNewBorn(String mother, String child, ESex sexOfNewBorn) {
    Optional<Person> newAdditionToFamily = personOperationsResolver.addChild(mother.toLowerCase(), child.toLowerCase(), sexOfNewBorn);
    return outputGenerator.getOutputForNewBornRequest(newAdditionToFamily, mother, child);
  }

}
