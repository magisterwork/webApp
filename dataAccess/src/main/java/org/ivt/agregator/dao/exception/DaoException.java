package org.ivt.agregator.dao.exception;

public class DaoException extends RuntimeException {

    public DaoException() {
    }

    public DaoException(String s) {
        super(s);
    }

    public DaoException(Exception e) {
        super(e);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
