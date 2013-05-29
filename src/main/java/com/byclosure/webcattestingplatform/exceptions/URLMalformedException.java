package com.byclosure.webcattestingplatform.exceptions;

/**
 *
 */
public class URLMalformedException extends RuntimeException {

    public URLMalformedException(Exception e) {
        super(e);
    }

    public URLMalformedException(String message) {
        super(message);
    }
}
