package pl.sda.intermediate11.bookstore.users.exceptions;

public class UserNotExistsException extends RuntimeException {
    private static final long serialVersionUID = -1879234821590149545L;

    public UserNotExistsException(String message) {
        super(message);
    }
}
