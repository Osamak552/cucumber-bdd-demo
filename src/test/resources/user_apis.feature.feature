Feature: Get User by ID
  As a user
  I want to retrieve a user by their ID
  So that I can view their details



  Scenario: Retrieve an existing user by ID
    When I request to retrieve user by ID 1
    Then the response status code should be 200
    And the response body should contain the following user details
