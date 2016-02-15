Feature: File manager - Sharing

    Scenario: User can share a file
        Given 'Thibaut' add 'test.txt' file
        When 'Thibaut' share 'test.txt' file to 'Maxime, Charles' with 'GET' permission
        Then 'test.txt' file is shared to 'Maxime, Charles' with 'GET' permission

    Scenario: User cannot share a file that doesn't belong to him
        Given 'Thibaut' add 'test.txt' file
        When 'Maxime' share 'test.txt' file to 'Charles, Yannick' with 'GET' permission
        Then 'test.txt' file is not shared to 'Maxime, Charles'

    Scenario: Shared users with GET permission can get shared file
        Given 'Thibaut' add 'test.txt' file
        When 'Thibaut' share 'test.txt' file to 'Maxime, Charles' with 'GET' permission
        Then 'Maxime, Charles' can get 'test.txt' file

    Scenario: Shared users with GET permission cannot delete a shared file
        Given 'Thibaut' add 'test.txt' file
        When 'Thibaut' share 'test.txt' file to 'Maxime, Charles' with 'GET' permission
        Then 'Maxime, Charles' cannot delete 'test.txt' file

    Scenario: Shared users with GET_AND_DELETE permission can get shared file
        Given 'Thibaut' add 'test.txt' file
        When 'Thibaut' share 'test.txt' file to 'Maxime, Charles' with 'GET_AND_DELETE' permission
        Then 'Maxime, Charles' can get 'test.txt' file

    Scenario: Shared users with GET_AND_DELETE permission can delete a shared file
        Given 'Thibaut' add 'test.txt' file
        When 'Thibaut' share 'test.txt' file to 'Maxime' with 'GET_AND_DELETE' permission
        Then 'Maxime' can delete 'test.txt' file

    Scenario: Shared users cannot share a shared file
        Given 'Thibaut' add 'test.txt' file
        And 'Thibaut' share 'test.txt' file to 'Maxime' with 'GET' permission
        And 'Thibaut' share 'test.txt' file to 'Charles' with 'GET_AND_DELETE' permission
        When 'Maxime' share 'test.txt' file to 'Yannick' with 'GET' permission
        And  'Charles' share 'test.txt' file to 'Luc' with 'GET' permission
        Then 'Yannick' cannot get 'test.txt' file
        And 'Luc' cannot get 'test.txt' file
