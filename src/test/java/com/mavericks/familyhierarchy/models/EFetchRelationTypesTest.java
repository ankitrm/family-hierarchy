package com.mavericks.familyhierarchy.models;

import static org.junit.Assert.*;

import org.junit.Test;

public class EFetchRelationTypesTest {

  @Test
  public void getDisplayableRelationShouldReturnSingularRelation() {
    final EFetchRelationTypes relationType = EFetchRelationTypes.BROTHERS;

    final String actualRelation = EFetchRelationTypes.getDisplayableRelation(relationType, true);

    assertEquals("Brother",actualRelation);
  }

  @Test
  public void getDisplayableRelationShouldNotChangeRelationIfPlural() {
    final EFetchRelationTypes relationType = EFetchRelationTypes.BROTHERS;

    final String actualRelation = EFetchRelationTypes.getDisplayableRelation(relationType, false);

    assertEquals("Brothers",actualRelation);
  }

  @Test
  public void getDisplayableRelationShouldNotChangeRelationIfSingular() {
    final EFetchRelationTypes relationType = EFetchRelationTypes.BROTHERS;

    final String actualRelation = EFetchRelationTypes.getDisplayableRelation(relationType, false);

    assertEquals("Brothers",actualRelation);
  }
}