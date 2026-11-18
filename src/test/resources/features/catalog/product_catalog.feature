Feature: Product Catalog
  As a Customer,
  I want search, filter and sort products in the catalog
  So that I can find I need quickly.
  Alessio is an on-line Customer

Rule: Customers should be able to search for products By Name
  Example: The one where Alessio searches for a Pliers
    Given Alessio is on the Home Page
    When he searches for "Pliers"
    Then the "Pliers" product should be displayed

    Example: The one where Alessio searches for a general product
      Given Alessio is on the Home Page
      When he searches for "saw"
      Then the following products should be displayed:
        | Product      | Price  |
        | Wood Saw     | $12.18 |
        | Circular Saw | $80.19 |
