package tech.itpark.app.exception;

public class UserAlreadyHaveThisRoleException extends RuntimeException{
    public UserAlreadyHaveThisRoleException() {
        super();
    }

    public UserAlreadyHaveThisRoleException(String message) {
        super(message);
    }

    public UserAlreadyHaveThisRoleException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyHaveThisRoleException(Throwable cause) {
        super(cause);
    }

    protected UserAlreadyHaveThisRoleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
