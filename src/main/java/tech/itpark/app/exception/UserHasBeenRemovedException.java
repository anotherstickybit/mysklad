package tech.itpark.app.exception;

public class UserHasBeenRemovedException extends RuntimeException{
    public UserHasBeenRemovedException() {
        super();
    }

    public UserHasBeenRemovedException(String message) {
        super(message);
    }

    public UserHasBeenRemovedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserHasBeenRemovedException(Throwable cause) {
        super(cause);
    }

    protected UserHasBeenRemovedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
