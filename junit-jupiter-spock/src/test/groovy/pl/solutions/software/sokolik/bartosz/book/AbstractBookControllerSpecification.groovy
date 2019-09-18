package pl.solutions.software.sokolik.bartosz.book

import spock.lang.Shared
import spock.lang.Specification

class AbstractBookControllerSpecification extends Specification {

    @Shared
    UUID id = UUID.fromString("7b1ccba0-3fdc-4fef-a392-3f5f3eceb979")

    @Shared
    String bookCreatedMessage = "Book created";

    @Shared
    String bookFoundMessage = "Success";

    @Shared
    String bookNotFoundMessage = "Book not found";

    @Shared
    String title = "Harry Potter"

    @Shared
    String isbn = "1234"

}
