Feature: File manager - Hook

    Scenario: When user add file an event is triggered
        When 'Thibaut' add 'test.txt' file
        Then add file event is triggered with 'Thibaut' owner and 'test.txt' file

    Scenario: When user delete file an event is triggered
        Given 'Thibaut' add 'test.txt' file
        When 'Thibaut' delete 'test.txt' file
        Then delete file event is triggered with 'Thibaut' owner and 'test.txt' file

    Scenario: When user share a file event is triggered
        Given 'Thibaut' add 'test.txt' file
        When 'Thibaut' share 'test.txt' file to 'Maxime, Charles' with 'GET' permission
        Then share a file event is triggered with 'Thibaut' owner and 'test.txt' file and 'Maxime, Charles' shared user and 'GET' permission