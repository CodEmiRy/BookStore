package pl.sda.intermediate11.bookstore.users.exceptions;

public class PasswordDoesNotMatchException extends RuntimeException {

    private static final long serialVersionUID = -5918941582163576488L;

    public PasswordDoesNotMatchException(String message) {
        super(message);
    }
}

