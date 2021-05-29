package tech.itpark.app.exception;

public class UserDoesntHaveThisRoleException extends RuntimeException{
    public UserDoesntHaveThisRoleException() {
        super();
    }

    public UserDoesntHaveThisRoleException(String message) {
        super(message);
    }

    public UserDoesntHaveThisRoleException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDoesntHaveThisRoleException(Throwable cause) {
        super(cause);
    }

    protected UserDoesntHaveThisRoleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
