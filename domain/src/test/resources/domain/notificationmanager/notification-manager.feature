Feature: Notification manager

  Scenario: User enable 'add file' and 'get file' notification without service send notification for all services
    Given 'Thibaut' set 'ADD, GET' notification setting to 'true' for ''
    When 'Thibaut' receive 'ADD, GET' notification
    Then 'Thibaut' has 'ADD, GET' notification through all services

  Scenario: User enable 'add file' and 'get file' notification through ServiceOne and ServiceTwo send notification for ServiceOne and ServiceTwo
    Given 'Thibaut' set 'ADD, GET' notification setting to 'true' for 'ServiceOne, ServiceTwo'
    When 'Thibaut' receive 'ADD, GET' notification
    Then 'Thibaut' has 'ADD, GET' notification from 'ServiceOne, ServiceTwo'

  Scenario: User enable 'add file' and 'get file' notification through ServiceOne send notification for ServiceOne
    Given 'Thibaut' set 'ADD, GET' notification setting to 'true' for 'ServiceOne'
    When 'Thibaut' receive 'ADD, GET' notification
    Then 'Thibaut' has 'ADD, GET' notification from 'ServiceOne'
    But 'Thibaut' has not 'ADD, GET' notification from 'ServiceTwo'

  Scenario: User enable 'add file' notification through ServiceOne and enable 'get file' notification through ServiceTwo send 'add file' notification from ServiceOne and 'get file' notification from ServiceTwo
    Given 'Thibaut' set 'ADD' notification setting to 'true' for 'ServiceOne'
    And 'Thibaut' set 'GET' notification setting to 'true' for 'ServiceTwo'
    When 'Thibaut' receive 'ADD, GET' notification
    Then 'Thibaut' has 'ADD' notification from 'ServiceOne'
    But 'Thibaut' has not 'GET' notification from 'ServiceOne'
    And 'Thibaut' has 'GET' notification from 'ServiceTwo'
    But 'Thibaut' has not 'ADD' notification from 'ServiceOne'

  Scenario: User set an existing service throw UnknownNotificationServiceException
    Then 'Thibaut' set 'ADD, GET' notification setting to 'true' for 'UnknownService', 'UnknownNotificationServiceException' is throw