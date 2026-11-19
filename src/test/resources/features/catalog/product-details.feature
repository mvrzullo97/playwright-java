Feature:
  As a Customer,
  I want see the product details, check its quantity
  and verify the possibility to add it in a cart
  Alessio is an on-line customer

  Background:
    Given Alessio is on the Home Page

  Scenario: The one where Alessio searches for Pliers and clicks on a product to see its details
    When he searches for "pliers"
    And he selects "Combination Pliers"
    Then the product details of "Combination Pliers" should be displayed

  Scenario: The one where Alessio searches for a out-of-stock product and clicks on a product to see
            that is not possible add it on a cart
    When he searches for "pliers"
    And he selects "Long Nose Pliers"
    Then the product should not be able to added to the cart

  Scenario: The one where Alessio selects Measuring Tape and clicks on a product to check the active co2 rate
    When he switches to page "3"
    And he selects "Measuring Tape"
    Then the co2 active rate should be "C"

  Scenario: The one where Alessio selects a product, increases the quantity, adds it to the cart the cart icon
            should be displayed with the right quantity
    When he selects "Bolt Cutters"
    And he increases the product quantity of 3
    And adds product to the cart
    Then the cart icon should be displayed with quantity of 3