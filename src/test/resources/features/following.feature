Feature:  Users can follow other users timelines

  Scenario: Following a user
    Given a user with posts on their timeline
    And another user with posts on their timeline
    And the user follows the other user
    When the user views their wall they see posts for everyone they are following
    And the user views their wall they see their own posts

  Scenario: Following a non existent user