package com.byclosure.webcat.webcattestingplatform.exceptions;

/**
 *
 */
public class PageObjectNotFoundException extends RuntimeException {

    public PageObjectNotFoundException(Exception ex) {
        super(ex);
    }

    public PageObjectNotFoundException(String message) {
        super(message);
    }
}
