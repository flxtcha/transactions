Feature: Money Transfer
  As a user
  I want to transfer money between my accounts
  So I can manage my finances

  Scenario: Successful money transfer
    Given Bob has a balance of 5000
    When he transfers 500 from his account
    Then his account should have a balance of 4500