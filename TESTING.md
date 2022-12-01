## Testing

Testable architecture - when team writes tests without using mocking frameworks

Avoid mocking frameworks - significantly decrease tests performance and negatively affect application architecture testability.

Removed mokk

Issues with mokk - multiple ways to verify

## Build configuration

`afterEvaluate` replaced with `withPlugin`

## Avoid sharing data trough test class fields - this will help with more efficient parallel tests execution

## Constructors accept only interfaces

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
