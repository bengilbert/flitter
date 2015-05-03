Welcome to this simple twitter clone.  The application allows you to

* post messages as any user
* view messages for any user
* follow any other user
* view messages from yourself and all the users you are following (wall)
* view you own messages (timeline)

Additionally you will find the following

* Spring Java Configuration - no xml files
* Spring @Configurable support to facilitate with DDD patterns.  
* Cucumber-jvm BDD tests - with World object to share state between steps
* Java8 Lambdas and currying

## Dependencies

* Java8
* maven3

## How to build and run

The simplest way to run the application after cloning is:

```
mvn clean compile exec:java
```

This will present you with a fairly minimalist user interface:

```
> 
```

From here you can post messages to a wall, view messages from any other user and also follow other users.

| Action             | Syntax | Example |
| Posting            | <user-name> -> <message to post> | Rebecca -> Just ran my first marathon.  Feeling estatic! | 
| Following          | <user-name> follows <user-name-to-follow> | Rebecca follows Martin |
| Viewing a timeline | <user-name> | Rebecca |
| Viewing a wall     | <user-name> wall | Rebecca wall |

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











