package com.byclosure.webcattestingplatform.exceptions;

/**
 *
 */
public class PageObjectNotFound extends RuntimeException {

    public PageObjectNotFound(Exception ex) {
        super(ex);
    }

    public PageObjectNotFound(String message) {
        super(message);
    }
}
