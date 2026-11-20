Feature:
  As an on-line Customer,
  I want register my account and check if the operation is successfully,
  After the registration, I want check if the user can log in with his credentials
  Alessio is a new on-line customer not registered yet

  Background:
    Given On-Line Customer is on the Login Page

  Scenario: the one where Alessio tries to log in with invalid credentials
    When he logs with "randomcredentials@gmail.com" and "random@password90"
    Then an error message "Invalid email or password" should be displayed

  Scenario Outline: the one where Alessio does not fill a mandatory field and tries to log in
    When he logs with "randomcredentials@gmail.com" and "random@password90"
    And he deletes the field "<Field>"
    Then an error message "<Error message>" should be displayed
    Examples:
      | Field           | Error message        |
      | Your email      | Email is required    |
      | Your password   | Password is required |

  Scenario: the one where Alessio has valid credentials and logs in successfully
    When he logs with "ale.marz@gmail.com" and "Marz@777"
    Then the account page should be displayed

  Scenario: the one where Alessio is a new customer and wants register your account
    And he goes to the Registration Page
    When he compiles correctly the Registration Form
    And submits the Registration Form
    Then the Login Page should be displayed