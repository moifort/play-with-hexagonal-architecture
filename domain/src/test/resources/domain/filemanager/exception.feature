Feature: File manager - Exception

    Scenario: User retrieve non existing file throw FileNotFoundException
        When 'Thibaut' do nothing
        Then 'Thibaut' try to get 'test.txt' file, 'FileNotFoundException' is throw

    Scenario: User delete non existing file throw FileNotFoundException
        When 'Thibaut' do nothing
        Then 'Thibaut' try to delete 'test.txt' file, 'FileNotFoundException' is throw

    Scenario: User share non existing file throw FileNotFoundException
        When 'Thibaut' do nothing
        Then 'Thibaut' try to share 'test.txt' file to 'Maxime, Charles' with 'GET' permission, 'FileNotFoundException' is throw

    Scenario: User retrieve a file that doesn't belong to him throw AccessDeniedException
        When 'Thibaut' add 'test.txt' file
        Then 'Maxime' try to get 'test.txt' file, 'AccessDeniedException' is throw

    Scenario: User delete a file that doesn't belong to him throw AccessDeniedException
        When 'Thibaut' add 'test.txt' file
        Then 'Maxime' try to delete 'test.txt' file, 'AccessDeniedException' is throw

    Scenario: User share a file that doesn't belong to him throw AccessDeniedException
        When 'Thibaut' add 'test.txt' file
        Then 'Maxime' try to share 'test.txt' file to 'Maxime, Charles' with 'GET' permission, 'AccessDeniedException' is throw
