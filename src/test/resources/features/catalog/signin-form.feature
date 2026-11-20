Feature:
  As an on-line Customer,
  I want register my account and check if the operation is successfully,
  After the registration, I want check if the user can log in with his credentials
  Alessio is a new on-line customer not registered yet

  Rule: in order to verify the functionalities of registration account
  Background:
    Given A Customer is on the Home Page
    And he goes to Login Page

  Scenario: try to log in with invalid credentials
    When he inserts invalid credentials
    And tries to submit the form
    Then an error message "Invalid email or password" should be displayed

  Scenario Outline: try to log in without compiling all mandatory filed
    When he inserts invalid credentials
    And he deletes the field "<Field>"
    And tries to submit the form
    Then an error message "<Error message>" should be displayed
    Examples:
      | Field           | Error message        |
      | Your email      | Email is required    |
      | Your password   | Password is required |

  Scenario: log in successfully
    When he logs in successfully with "ale.marz19@gmail.com" and "Marz@777"
    Then the account page should be displayed

  Scenario: register a new account from zero
    And he goes to the Registration Page
    When he compiles correctly the Registration Form
    And submits the Registration Form
    Then the Login Page should be displayed