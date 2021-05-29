package tech.itpark.app.exception;

public class WrongSecretException extends RuntimeException{
    public WrongSecretException() {
        super();
    }

    public WrongSecretException(String message) {
        super(message);
    }

    public WrongSecretException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongSecretException(Throwable cause) {
        super(cause);
    }

    protected WrongSecretException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
