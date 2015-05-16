## Overview

Welcome to this simple twitter clone.  The application allows you to

* post messages as any user
* view messages for any user
* follow any other user
* view messages from yourself and all the users you are following (wall)
* view you own messages (timeline)

Additionally you will find the following

* Spring Java Configuration - no xml files
* Spring @Configurable support to facilitate with DDD patterns.  
* Cucumber-jvm BDD tests
* Java8 Lambdas and currying

## This looks familiar!

Well, yes it does!  A quick search for twitter clones on github reveals a number which perhaps just might have been
used as coding tests for potential job interviews.  If you are looking at this code then you may wish to consider some 
of the feedback I have received:

* BDD test don't interact with real domain. Class World introduce emulation of interaction and it is not necessary.
* Repositories contain method "reset" which is used only by tests. Mocks could be used instead. 
* Insufficient unit test. No unit tests for classes in renderer package. No unit test for class CommandInterpreter.
* Class CommandInterpreter have two different responsibilities: parsing and executing a command. 
* Class ResponseRenderer is very complex and doesn't have corresponding test which could explain its behaviours. Method "render" is in package scope from unknown reason. 
* Functional interfaces in package renderer could be in package scope. They are not used outside the package
* Behaviour of each command is encapsulated in one class Command. This class doesn't have single responsibility.
* single characters names of variables and parameters (e.g. in class CommandBuilder, User, ResponseRenderer). It is hard to understand purpose of each parameter or variable.
* confusing name for message repository 

## Think you can do a better job?

Send me a pull request, i'd be especially interested other options for simplfying the BDD tests

## Dependencies

* Java8
* maven3

## How to build and run

The simplest way to run the application after cloning is:

```
mvn clean compile exec:java
```

Or, you can run one of the pre-built releases using `java -jar flitter-VX.X-with-dependencies.jar` (where X.X is the version number you have downloaded) for example:

```
java -jar flitter-1.0-jar-with-dependencies.jar
```

Note : you will need java8 installed to run the jar file.

## Runnning

After running the application you will be presented with a fairly minimalist user interface:

```
> 
```

From here you can post messages to a wall, view messages from any other user and also follow other users.

| Action             | Syntax | Example |
|--------------------|--------|---------|
| Posting            | `<user-name> -> <message to post>` | `Rebecca -> Just ran my first marathon.  Feeling estatic!` | 
| Following          | `<user-name> follows <user-name-to-follow>` | `Rebecca follows Martin` |
| Viewing a timeline | `<user-name>` | `Rebecca` |
| Viewing a wall     | `<user-name> wall` | `Rebecca wall` |

To exit the application press enter at the command prompt.

## Examples

```
> Rebecca -> Just ran my first marathon.  Feeling estatic!
> Rebecca -> Regretting not eating after finishing my race.  Really tired :(
> Martin -> Anyone know a good recipe for Apple pie?  
> Martin -> Oh, and roast chicken?
> Rebecca follows Martin
> Rebecca wall
Martin - Oh, and roast chicken? (12 seconds ago)
Martin - Anyone know a good recipe for Apple pie? (33 seconds ago)
Rebecca - Regretting not eating after finishing my race.  Really tired :( (1 minute ago)
Rebecca - Just ran my first marathon.  Feeling estatic! (1 minute ago)
> Martin
Oh, and roast chicken? (1 minute ago)
Anyone know a good recipe for Apple pie? (1 minute ago)
> 
```











