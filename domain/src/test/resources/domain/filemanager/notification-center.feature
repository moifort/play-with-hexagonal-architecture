Feature: File manager - Notification center

  Scenario: When user add file a notification is triggered
    When 'Thibaut' add 'test.txt' file
    Then 'ADD' is triggered with 'Thibaut' user name and 'test.txt' files id

  Scenario: When user get one file a notification is triggered
    Given 'Thibaut' add 'test.txt' file
    When 'Thibaut' can get 'test.txt' file
    Then 'GET' is triggered with 'Thibaut' user name and 'test.txt' files id

  Scenario: When user get all files a notification is triggered
    Given 'Thibaut' add 'test.txt' file
    Given 'Thibaut' add 'test-1.txt' file
    When 'Thibaut' get all files
    Then 'GET' is triggered with 'Thibaut' user name and 'test.txt, test-1.txt' files id

  Scenario: When user delete file a notification is triggered
    Given 'Thibaut' add 'test.txt' file
    When 'Thibaut' delete 'test.txt' file
    Then 'DELETE' is triggered with 'Thibaut' user name and 'test.txt' files id

  Scenario: When user get all shared files a notification is triggered
    Given 'Thibaut' add 'thibaut-file.txt' file
    And 'Maxime' add 'maxime-file.txt' file
    And 'Maxime' add 'maxime-file-not-shared.txt' file
    And 'Thibaut' share 'thibaut-file.txt' file to 'Charles' with 'GET' permission
    And 'Maxime' share 'maxime-file.txt' file to 'Charles' with 'GET' permission
    When 'Charles' get all shared files
    Then 'GET_SHARE' is triggered with 'Charles' user name and 'thibaut-file.txt, maxime-file.txt' files id

  Scenario: When user share a file a notification is triggered
    Given 'Thibaut' add 'test.txt' file
    When 'Thibaut' share 'test.txt' file to 'Maxime, Charles' with 'GET' permission
    Then 'SHARE_WITH' is triggered with 'Thibaut' owner and 'test.txt' file and 'Maxime, Charles' shared user and 'GET' permission