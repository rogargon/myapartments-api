Feature: Create Apartment
  In order to manage apartments
  As an Owner
  I want to create apartments

  Scenario: Create an apartment as Owner
    Given There is a registered owner with username "owner" and password "password" and email "owner@example.com"
    And I login as "owner" with password "password"
    When I create an apartment with name "Cozy Loft", floor "3", address "Carrer de les Flors 10", postal code "08001", city "Barcelona", country "Spain", description "A cozy loft in the center of Barcelona"
    Then The response code is 201
    And The apartment has been created with name "Cozy Loft", floor "3", address "Carrer de les Flors 10", postal code "08001", city "Barcelona", country "Spain", description "A cozy loft in the center of Barcelona"