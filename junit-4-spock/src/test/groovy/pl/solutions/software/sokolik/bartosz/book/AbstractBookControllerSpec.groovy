package pl.solutions.software.sokolik.bartosz.book

import spock.lang.Specification

abstract class AbstractBookControllerSpec extends Specification {

    static final UUID ID = UUID.fromString("7b1ccba0-3fdc-4fef-a392-3f5f3eceb979")
    static final String BOOK_CREATED_MESSAGE = "Book created";
    static final String BOOK_FOUND_MESSAGE = "Success";
    static final String BOOK_NOT_FOUND_MESSAGE = "Book not found";
    static final String TITLE = "Harry Potter"
    static final String ISBN = "1234"

}
