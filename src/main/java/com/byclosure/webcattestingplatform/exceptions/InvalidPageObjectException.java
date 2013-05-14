package com.byclosure.webcattestingplatform.exceptions;

import com.byclosure.webcattestingplatform.pageobjects.IPageObject;


/**
 *
 */
public class InvalidPageObjectException extends RuntimeException {

    public InvalidPageObjectException() {
    }

    public InvalidPageObjectException(String message) {
        super(message);
    }

    public InvalidPageObjectException(String message, Exception ex) {
        super(message, ex);
    }

    public InvalidPageObjectException(IPageObject page, Exception ex) {
        super(page.getClass().getName() + " is invalid", ex);
    }
}
