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

    Example: The one where Alessio searches for a product that doesn't exist
      Given Alessio is on the Home Page
      When he searches for "Product-does-not-exist"
      Then no product should be displayed
      And the message "There are no products found." should be displayed

  Rule: Customer should be able to narrow downs their search by category
    Example: The one where Alessio only wants to see Hand Saw
      Given Alessio is on the Home Page
      When he filters by "Hand Saw"
      Then the "Wood Saw" product should be displayed

  Rule: Customer should be able to sort products by various criteria
    Scenario Outline: Alessio sorts by different criteria
      Given Alessio is on the Home Page
      When he sorts by "<Sort>"
      Then the first product displayed should be "<First Product>"
      Examples:
        | Sort | First Product |
        | Name (A - Z) | Adjustable Wrench |
        | Name (Z - A) | Wood Saw          |
        | Price (High - Low) | Drawer Tool Cabinet |
        | Price (Low - High) | Washers             |

