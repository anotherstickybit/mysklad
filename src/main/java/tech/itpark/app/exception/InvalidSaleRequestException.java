package tech.itpark.app.exception;

public class InvalidSaleRequestException extends RuntimeException{
    public InvalidSaleRequestException() {
        super();
    }

    public InvalidSaleRequestException(String message) {
        super(message);
    }

    public InvalidSaleRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSaleRequestException(Throwable cause) {
        super(cause);
    }

    protected InvalidSaleRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
