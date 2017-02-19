package org.ivt.agregator.integration.httpUtils.exception;

public class HttpRequestException extends RuntimeException {

    public HttpRequestException(Exception ex) {
        super(ex);
    }

    public HttpRequestException(String s) {
        super(s);
    }
}
