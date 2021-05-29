package tech.itpark.app.exception;

public class WareHouseNotFoundException extends RuntimeException {
    public WareHouseNotFoundException() {
        super();
    }

    public WareHouseNotFoundException(String message) {
        super(message);
    }

    public WareHouseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public WareHouseNotFoundException(Throwable cause) {
        super(cause);
    }

    protected WareHouseNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
