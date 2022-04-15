Feature: Smoke tests

  Scenario: Creating training event

    Given I logged in SnowIQ using "EBT Login" as "admin.snowiq@eastbanctech.com" with password "WarmDays123!"
    When I open Event Management Page from sidebar
    And I click button " Create New Storm Event "
    Then New Event form is shown
    When I submit form New Event with parameters
      | fieldName                                    | value               |
      | Event Name                                   | oksa training event |
      | Activation Time                              | Today               |
      | Predicted Precipitation (inches)             | 4                   |
      | Predicted Duration (hours)                   | 1                   |
      | Number of employees planned to participate   | 5                   |
      | Number of contractors planned to participate | 5                   |
      | Training                                     | true                |
    And I filter event list with training including
    Then I see "oksa trainng event" in list of storm events

  Scenario: Login
    Given


