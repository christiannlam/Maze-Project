# HW 2 Grading

## Score: 92 out of 100 points

## Feedback from code review

### contributions.txt
* Christian Lam - 50
* Gideon Essel - 50

### JavaDoc Comments: 14 out of 15 points

* + @author
* - Missing/Inacurate JavaDoc comments
  * addPassage(): @param descriptions of a and b
  * in solve(), the tag is @return (singular, not plural)

### Code quality: 28 out of 35 points

#### addPassage()

* - Incorrectly using literal values 0, 1, 2, 3. You need to use the constants NORTH, EAST, SOUTH, WEST. Remember that constants are defined to avoid using literal values.

#### addToFront()

* + Ok
* . The second dimension is 2, and start has length 2, since they are pairs of numbers. That is given, so using these facts leads to more concise/clear code.

#### chooseRandomlyFrom()

* - Not quite. This can lead the program to a runtime exception. The value sent to nextInt() must be positive, yet, sometimes this method is called with n being 0, which is not a positive number.

#### contains()

* - Incorrectly continuing the loop even though linear search already succeeded in finding the pair

#### expandLocation()

* Good
* - Not satisfying requirement: "only one return instruction"

#### expandMaze()

* + Good

#### remove()

* - Repetitive code. This method needs to find where the pair is, just like contains() also needed to find it. 
* - Incorrectly continuing the loop even though linear search already succeeded in finding the pair


#### solve()

+ Good

## Running program and unit tests: 50 out of 50 points
### Result from running program
** + Good! **

### Unit test results...
```
-------------------------------------------------------------------------------
Test set: cecs274.MazeTest
-------------------------------------------------------------------------------
Tests run: 21, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.126 s - in cecs274.MazeTest
```
