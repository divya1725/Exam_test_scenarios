# Predefined creditor / Payment template SOAP Project

Predefined Creditor see : https://fswiki.evry.com/display/product/Initiate+and+Maintain+Pre-defined+Creditor
Payment template see: https://fswiki.evry.com/pages/viewpage.action?spaceKey=projects&title=Payment+Templates

## Getting Started
------


## Step to be followed to maintain soap project
------
- Import correct properties on test suites level from /properties directory.
- Change environment to correct test environment, Warning : Never run cava bank tests to Norway environment and visa versa.
- When you run private market tests, Skip 'Auth' test cases under each suite. May be disable it.
- Never create authorisation record with other channel than : BES.
- GS4, GD17 are cava test environments.

## Commit message format should be one of the following
------
* `PIDOM-1234: some message`
* `PRM-1234: some message`
* `NOJIRA: some message"`
* `Revert "PIDOM-1234: some message"`
* `Revert "PRM-1234: some message"`

