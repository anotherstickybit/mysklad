package tech.itpark.app.exception;

public class InvalidDataRangeException extends RuntimeException {
    public InvalidDataRangeException() {
        super();
    }

    public InvalidDataRangeException(String message) {
        super(message);
    }

    public InvalidDataRangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDataRangeException(Throwable cause) {
        super(cause);
    }

    protected InvalidDataRangeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
