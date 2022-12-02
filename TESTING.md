## Testing

Testable App Architecture criteria:
 - Code uses Functional programming concepts when applicable
   - Pure functions, immutable classes, method references etc.
 - Code can be tested without mocking frameworks
   - DI implementation uses interfaces when objects needs to be passed to a constructor 
 - Business logic fully separated/abstracted from UI and platform
   - Ideally VM's tests should be able to run in pure JVM environment e.g. no dependencies on Android SDK or Android libs.

## Why avoid mocking frameworks

- Mocking frameworks usage reduce the incentive for developers to invest in testable code since any testability issues can be easily hacked with mocks.
- Mocking frameworks rely on reflection, which significantly slows down unit tests 
- Dramatically increase memory consumption during execution, which can lead to OOM issues in local and CI environment
- Creates ambiguity during writing the tests e.g. expected result could be validated via junit or mokk api's

## Avoid sharing data trough test class fields - this will help with more efficient parallel tests execution

## Best practices for writing unit tests

- Avoid using mocks
- Avoid shared objects across test, use parametrised test init methods instead
  - this approach will help to more efficiently utilize tests parallelization
- Use test implementations of interfaces which returns fake data
- Implement helper API's with tests specific functionality

## Testing plan

- Remove mocks
- Split screens into modules
- Every module have per screen UI/Screenshot tests
- Split components into modules
- Every component implements interface
- Every component has test implementation which is used instead of mocks
- Use function references instead of stateless classes
  - Easier to test
  - less code
  - no need for inject constructors
- Avoid testing implementation details, test behaviors e.g. ViewModels, UseCase, etc.
- Generate test implementations for retrofit services
