Feature: File manager - Handler event

  Scenario: When user add file an event is triggered
    When 'Thibaut' add 'test.txt' file
    Then 'addFileEvent' is triggered with 'Thibaut' user name and 'test.txt' files id

  Scenario: When user get one file an event is triggered
    Given 'Thibaut' add 'test.txt' file
    When 'Thibaut' can get 'test.txt' file
    Then 'getFileEvent' is triggered with 'Thibaut' user name and 'test.txt' files id

  Scenario: When user get all files an event is triggered
    Given 'Thibaut' add 'test.txt' file
    Given 'Thibaut' add 'test-1.txt' file
    When 'Thibaut' get all files
    Then 'getFileEvent' is triggered with 'Thibaut' user name and 'test.txt, test-1.txt' files id

  Scenario: When user delete file an event is triggered
    Given 'Thibaut' add 'test.txt' file
    When 'Thibaut' delete 'test.txt' file
    Then 'deleteFileEvent' is triggered with 'Thibaut' user name and 'test.txt' files id

  Scenario: When user get all shared files an event is triggered
    Given 'Thibaut' add 'thibaut-file.txt' file
    And 'Maxime' add 'maxime-file.txt' file
    And 'Maxime' add 'maxime-file-not-shared.txt' file
    And 'Thibaut' share 'thibaut-file.txt' file to 'Charles' with 'GET' permission
    And 'Maxime' share 'maxime-file.txt' file to 'Charles' with 'GET' permission
    When 'Charles' get all shared files
    Then 'getSharedFileEvent' is triggered with 'Charles' user name and 'thibaut-file.txt, maxime-file.txt' files id

  Scenario: When user share a file event is triggered
    Given 'Thibaut' add 'test.txt' file
    When 'Thibaut' share 'test.txt' file to 'Maxime, Charles' with 'GET' permission
    Then 'shareFileEvent' is triggered with 'Thibaut' owner and 'test.txt' file and 'Maxime, Charles' shared user and 'GET' permission