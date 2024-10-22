Feature: Delete Room
  In order to use the app
  As a owner
  I must be able to delete Room

  Background:
    Given There is a registered owner with username "owner1" and password "password" and email "owner1@sample.app"
    Given There is a apartment with the name "testApartment", floor "0", address "roomAddressX", postal code "postalCodeX", city "cityX", country "countryX", description "roomDescriptionX" and a creation date "2024-10-18T17:44:30.551316+00:00" by owner username "owner"
    Given There is a room with surface "30", is occupied "true", has window "true", has desk "false" and have bed "true", by owner username "owner" and the apartment_name "testApartment"
    
  Scenario: Delete a Room with owner and room not logged in
    Given I'm not logged in
    When I try to delete Room of the apartment "testApartment"
    Then The response code is 401
    
  Scenario: Delete a Room with owner and room logged in
    Given I login as "owner1" with password "password"
    When I try to delete Room of the apartment "testApartment"
    Then The response code is 200