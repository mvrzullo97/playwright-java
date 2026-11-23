Feature:
  A new Customer proceeds to checkout his cart;
  - Sign-in phase
  - Billing Address phase
  - Payment phase

  Rule: verify the Sign-in (or guest) phase
    Scenario: checkout a product as a logged customer
      Given a new random customer registers your account and logs in
      And he selects "Circular Saw" and adds it to the cart
      When he proceeds to checkout
      Then the checkout phase message "Hello Random Customer, you are already logged in." should be displayed

    Scenario: checkout a product as a guest
      Given a Customer is on the Home Page
      And he selects "Circular Saw" and adds it to the cart
      When he proceeds to checkout
      Then a log-in form should be displayed

    Scenario: compile and submit the guest form
      Given a Customer is on the Home Page
      And he selects "Circular Saw" and adds it to the cart
      When he proceeds to checkout
      And he compiles the guest form
      And he submits the guest form
      Then the checkout phase message "Continuing as guest:" should be displayed

    Scenario Outline: check guest form mandatory fields
      Given a Customer is on the Home Page
      And he selects "Circular Saw" and adds it to the cart
      When he proceeds to checkout
      And he compiles the guest form
      And he deletes "<Field name>"
      And he submits the guest form
      Then the alert message "<Alert message>" should be displayed
      Examples:
        | Field name      | Alert message          |
        | Your email      | Email is required      |
        | Your first name | First name is required |
        | Your last name  | Last name is required  |

  Rule: verify the Billing Address phase
    Scenario: compile the billing address form
      Given a Customer added "Hammer" to the cart and compiled guest form
      When he compiles the billing address form
      And he proceeds to checkout
      Then the payment phase should be displayed

  Rule: verify the Payment phase
    Scenario Outline: compile the payment form to confirm the order
      Given a Customer added "Bolt Cutters" to the cart and compiled guest form
      When he compiles the billing address form
      And he proceeds to checkout
      And he chooses the "<Payment method>" payment method
      And he confirms the payment
      Then the operation success message "Thanks for your order!" should be displayed
      And the cart icon should not be visible
      Examples:
        | Payment method  |
        | Bank Transfer   |
        |Cash on Delivery |
        |Credit Card      |
        |Buy Now Pay Later|
        |Gift Card        |






