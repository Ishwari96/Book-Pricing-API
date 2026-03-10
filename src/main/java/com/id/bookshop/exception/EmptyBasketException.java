package com.id.bookshop.exception;

/**
 * The Class EmptyBasketException
 */
public class EmptyBasketException extends RuntimeException {

    /**
     * Instantiates empty message exception
     *
     * @param message the message
     */
    public EmptyBasketException(String message) {
        super(message);
    }
}
