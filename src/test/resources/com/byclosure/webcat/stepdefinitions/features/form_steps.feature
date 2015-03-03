Feature: Form Steps
  
  Scenario: Fill text field
    When I fill in "input_id" with "some value"
    When I fill in "input_name" with "some value"
    When I fill in "Input Label" with "some value"
    When I fill in ".input-class" with "some value"
    When fill in "input_id" with "some value"
    
  Scenario: Check field contains
    Then the "input_selector" field should contain "some value"
    Then the "input_selector" field within "form" should contain "some value"
    
  Scenario: Click button
    When I press "button text"
    When press "button text"