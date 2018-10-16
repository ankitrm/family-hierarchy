package com.mavericks.familyhierarchy.models;

public enum EFetchRelationTypes {
  WIFE,
  HUSBAND,
  FATHER,
  MOTHER,
  BROTHER,
  BROTHERS,
  SISTER,
  SISTERS,
  SON,
  SONS,
  DAUGHTER,
  DAUGHTERS,
  COUSIN,
  COUSINS,
  GRANDMOTHER,
  GRANDFATHER,
  GRANDSON,
  GRANDSONS,
  GRANDDAUGHTER,
  GRANDDAUGHTERS,
  AUNT,
  AUNTS,
  UNCLE,
  UNCLES;

  public static String getDisplayableRelation(EFetchRelationTypes fetchRelationType, boolean isSingular) {
    String relationTypeString = fetchRelationType.toString();
    if (isSingular) {
      switch (fetchRelationType) {
        case BROTHERS:
        case SONS:
        case SISTERS:
        case DAUGHTERS:
        case COUSINS:
        case GRANDSONS:
        case GRANDDAUGHTERS:
        case AUNTS:
        case UNCLES:
          return relationTypeString.substring(0, 1).toUpperCase() +
              relationTypeString.substring(1, relationTypeString.length() - 1).toLowerCase();
      }
    }
    return relationTypeString.substring(0, 1).toUpperCase() +
        relationTypeString.substring(1, relationTypeString.length()).toLowerCase();
  }
}
