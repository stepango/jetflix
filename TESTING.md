## Testing

Testable App Architecture criteria:
 - Code uses Functional programming concepts when applicable
   - Pure functions, immutable classes, method references etc.
 - Code can be tested without mocking frameworks
   - DI implementation uses interfaces when objects needs to be passed to a constructor 
 - Business logic fully separated/abstracted from UI and platform
   - Ideally VM's tests should be able to run in pure JVM environment e.g. no dependencies on Android SDK or Android libs.

## Mocking testing data

Requirements:
- Easy switch between real/mocked data source
- Parallel executions for same test collection against real and mock data
- Multi-purpose tests
  - Single test could be executed against real|mock data 

# Hilt modules substitution

Pros:
- Easy to implement
- Officially supported

Cons:
- Not flexible
- Need multiple test implementations for mocked/read data
- No multi-purpose tests support

# Gradle project flags

Build configuration fields created based on flags passed in gradle command line
DataStores created by factory classes, implementation of stores would be based on flags

Pros:
- Flexible
- Support multi-purpose tests

Cons:
- No parallel test collections execution
- Not build-cache friendly

# Flavor based testing configurations

Pros:
- Support parallel execution
- Support multi purpose tests

Cons: 
- Harder to setup
- IDE support could be better

# Static hooks for DataStore factories

Pros:
- Easy to implement

Cons:
- May affect parallel tests execution on single worker
- No parallel test collections execution

# Code generation based on annotation processing
Original test marked with Annotation, got duplicated with different configuration before execution

Pros:
- flexible
- Support parallel execution
- Support multi purpose tests
- Can be combined with other technics

Cons:
- Implementation complexity

# Code generation Gradle plugin
Test folder is (partially) copied with alterred flags

Pros:
- Support parallel execution
- Support multi purpose tests
- Can be combined with other technics

Cons:
- Implementation complexity

# Parametrised tests (junit5) + Hilt flag based injection
Single test may have multiple parameters declared in annotation
Injector will swap data source implementation, based on parameter  

Pros:
- Support parallel execution
- Support multi purpose tests
- Can be combined with other technics

Cons: 
- Implementation complexity
- May not be possible with Hilt out-of-the-box


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

## Future plans 

- Remove mocks
- Split screens/features into modules
- Split business logic in separate modules (non-android)
- Every module have per screen UI/Screenshot tests
- Split components into modules
- Every component has test implementation which is used instead of mocks
- Use function references instead of stateless classes
  - Easier to test
  - less code
  - no need for inject constructors
- Avoid testing implementation details, test behaviors e.g. ViewModels, UseCase, etc.
- Generate test implementations for retrofit service interfaces
- Further scale app architecture with flavor of https://github.com/formatools/forma tailored for project needs
