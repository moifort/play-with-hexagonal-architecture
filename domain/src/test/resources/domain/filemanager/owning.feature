Feature: File manager - Owning

    Scenario: User cannot retrieve a file that doesn't belong to him
        Given 'Thibaut' add 'test.txt' file
        Then 'Maxime' cannot get 'test.txt' file

    Scenario: User cannot delete a file that doesn't belong to him
        Given 'Thibaut' add 'test.txt' file
        When 'Maxime' delete 'test.txt' file
        Then 'test.txt' file exist
        And 'Thibaut' can get 'test.txt' file
