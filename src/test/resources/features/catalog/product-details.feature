Feature: Product Details
  As a Customer,
  I want see the product details, check its quantity
  and verify the possibility to add it in a cart

  Background:
    Given a Customer is on the Home Page

  Scenario: search for Pliers and click on a product to see its details
    When he searches for "pliers"
    And he selects "Combination Pliers"
    Then the product details of "Combination Pliers" should be displayed

  Scenario: search for an out-of-stock product and try to add it to the a cart
    When he searches for "pliers"
    And he selects "Long Nose Pliers"
    Then the product should not be able to added to the cart

  Scenario: select a product and check the active co2 rate
    When he switches to page "3"
    And he selects "Measuring Tape"
    Then the co2 active rate should be "C"

  Scenario: increase the quantity of a product and add them to the cart
    When he selects "Bolt Cutters"
    And he increases the product quantity of 3
    And he adds product to the cart
    Then the cart icon should be displayed with quantity of 3

  Scenario: add a product to the favourite list
    And a new random customer registers your account and logs in
    And he goes to Home Page
    When he searches for "pliers"
    And he selects "Combination Pliers"
    And add the product to the Favourite List
    Then the "Combination Pliers" product should be displayed in the List

  Scenario: remove a product from the cart
    And he selects "Combination Pliers"
    And he adds product to the cart
    And he goes to Cart Page
    When he deletes the product form the cart
    Then the product "Combination Pliers" should not be displayed into the cart