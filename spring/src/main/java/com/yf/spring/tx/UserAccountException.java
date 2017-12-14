package com.yf.spring.tx;

public class UserAccountException extends RuntimeException {
    private static final long serialVersionUID = -4739460754210242638L;

    public UserAccountException() {
        super();
    }

    public UserAccountException(String message) {
        super(message);
    }

    public UserAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAccountException(Throwable cause) {
        super(cause);
    }

    protected UserAccountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
