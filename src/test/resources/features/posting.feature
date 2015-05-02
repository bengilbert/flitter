Feature: A user can post messages

  Scenario: Publish messages to a personal timeline
    Given a user
    When the user posts a message
    Then the message will be on the users personal timeline

