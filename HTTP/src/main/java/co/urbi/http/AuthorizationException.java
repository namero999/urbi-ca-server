package co.urbi.http;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

public class AuthorizationException extends UrbiException {

    private static final String MESSAGE = "Unauthorized";

    public AuthorizationException() {
        super(MESSAGE);
    }

    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return SC_UNAUTHORIZED;
    }

}