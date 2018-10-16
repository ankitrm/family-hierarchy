package com.mavericks.familyhierarchy.executors.impl;

import static com.mavericks.familyhierarchy.patterns.AddCommandPatterns.SUPPORTED_ADD_PATTERNS;
import static com.mavericks.familyhierarchy.patterns.FetchCommandPatterns.PATTERN_FETCH_PERSON_FROM_RELATION;

import com.mavericks.familyhierarchy.executors.AddCommandExecutor;
import com.mavericks.familyhierarchy.executors.CommandResolver;
import com.mavericks.familyhierarchy.executors.FetchCommandExecutor;
import com.mavericks.familyhierarchy.generators.OutputGenerator;

import java.util.regex.Matcher;
import javax.inject.Inject;

public class CommandResolverImpl implements CommandResolver {

  private final FetchCommandExecutor fetchCommandExecutor;
  private final AddCommandExecutor addCommandExecutor;
  private final OutputGenerator outputGenerator;

  @Inject
  CommandResolverImpl(FetchCommandExecutor fetchCommandExecutor,
                      AddCommandExecutor addCommandExecutor,
                      OutputGenerator outputGenerator) {
    this.fetchCommandExecutor = fetchCommandExecutor;
    this.addCommandExecutor = addCommandExecutor;
    this.outputGenerator = outputGenerator;
  }

  @Override
  public String resolve(String command) {
    final Matcher fetchCommandMatcher = PATTERN_FETCH_PERSON_FROM_RELATION.matcher(command);
    if (fetchCommandMatcher.matches()) {
      return fetchCommandExecutor.execute(command);
    } else {
      final Matcher addCommandMatcher = SUPPORTED_ADD_PATTERNS.matcher(command);
      if (addCommandMatcher.matches()) {
        return addCommandExecutor.execute(command);
      } else {
        return outputGenerator.getOutputForUnknownRequest(command);
      }
    }
  }
}
