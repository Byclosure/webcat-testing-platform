Feature: Browsing Steps
  
  Scenario: On page
    Given I am on "http://www.webcatproject.com"
    Given am on "http://www.webcatproject.com"
    
  Scenario: Go to page
    When I go to "http://www.webcatproject.com"
    When go to "http://www.webcatproject.com"
    
  Scenario: Follow link
    When I follow "link_name"
    When I follow "link_id"
    When I follow "Link Text"
    When I follow "Link Title"
    When I follow "Image with Link alt"
    When I follow ".link-class"
    When follow "link_name"
  
  Scenario: Check text exists
    Then I should see "some text in the page content"
    Then should see "some text in the page content"
    
  Scenario: Check on URL
    Then I should be on "http://www.webcatproject.com"
    Then should be on "http://www.webcatproject.com"