### Family Heirarchy

The application helps to create a family hierarchy / tree using add/fetch operations.

NOTE: All the commands are case insensitive.
 
##### Supported operations to add a member to the family:

1. Husband=<HUSBAND_NAME> Wife=<WIFE_NAME>

2. Wife=<HUSBAND_NAME> Husband=<WIFE_NAME>

3. Mother=<MOTHER_NAME> Son=<SON_NAME>

4. Son=<SON_NAME> Mother=<MOTHER_NAME>

5. Mother=<MOTHER_NAME> Daughter=<DAUGHTER_NAME>

6. Daughter=<DAUGHTER_NAME> Mother=<MOTHER_NAME>

##### Supported operations to add a member to the family:

1. Person=<PERSON_NAME> Relation=<RELATION_TYPE>

Supported Relation Types:

WIFE, HUSBAND, FATHER, MOTHER, BROTHER, BROTHERS, SISTER, SISTERS, SON, SONS, DAUGHTER,
DAUGHTERS, COUSIN, COUSINS, GRANDMOTHER, GRANDFATHER, GRANDSON, GRANDSONS, GRANDDAUGHTER,
GRANDDAUGHTERS, AUNT, AUNTS, UNCLE, UNCLES

##### How to run the application:

Once the project and extracted is downloaded locally:
1. Run `mvn clean package` to automatically run all the tests and jacoco code coverage
2. Visit the coverage file in `<PROJECT_PATH>/target/site/jacoco/index.html` to verify code coverage
3. Run the application using command `<PROJECT_PATH>/mvn clean compile exec:java`
4. At any time, type `exit/quit/q` (case insensitive) to exit the application 

The application automatically loads `init/InitialFamily.txt` as default family tree.
This setting can be found under `initial.file.name` specified in `src/main/resources/application.conf`
To Ignore the default family tree, modify `load.default.family` as `false` under `src/main/resources/application.conf`and re-run `mvn clean exec:java`

Following are the third party libraries used:
1. Jacoco code coverage: To ensure appropriate code coverage for the unit tests
2. JUnit: To build and run JUnit tests
3. Mockito-Core: To mock classes for more effective unit tests (verifying parameters, number of calls and results)
4. Guice: dependency injection engine for maintainable code and loose coupling between classes 