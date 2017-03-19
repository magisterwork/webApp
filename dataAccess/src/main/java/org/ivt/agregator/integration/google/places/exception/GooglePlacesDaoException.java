package org.ivt.agregator.integration.google.places.exception;

public class GooglePlacesDaoException extends RuntimeException {

    public GooglePlacesDaoException(String message, Throwable e) {
        super(message, e);
    }
}
