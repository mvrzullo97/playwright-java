Feature: Contact Form

  Scenario: compile correctly the form and submit
    Given a Customer is on the Home Page
    And he goes to Contact Page
    When he compiles correctly the contact form
    And he submits the contact form
    Then the contact message "Thanks for your message! We will contact you shortly." should be displayed

  Scenario Outline: check contact form mandatory fields
    Given a Customer is on the Home Page
    And he goes to Contact Page
    When he compiles correctly the contact form
    And he deletes the contact field "<Contact Field>"
    And he submits the contact form
    Then the error message "<Error Message>" should be displayed
    Examples:
      |Contact Field|Error Message|
      |First name   |First name is required|
      |Last name    |Last name is required|
      |Email address|Email is required|
      |Message *    |Message is required|