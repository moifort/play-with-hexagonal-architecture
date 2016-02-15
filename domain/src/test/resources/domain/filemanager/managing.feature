Feature: File manager - Managing

    Scenario: User can add file
        When 'Thibaut' add 'test.txt' file
        Then 'Thibaut' can get 'test.txt' file

    Scenario: User can delete file
        Given 'Thibaut' add 'test.txt' file
        When 'Thibaut' delete 'test.txt' file
        Then 'Thibaut' cannot get 'test.txt' file
        And 'test.txt' file doesn't exist

    Scenario: User cannot retrieve non existing file
        When 'Thibaut' do nothing
        Then 'Thibaut' cannot get 'test.txt' file

    Scenario: User cannot delete non existing file
        When 'Thibaut' do nothing
        Then 'Thibaut' cannot delete 'test.txt' file

