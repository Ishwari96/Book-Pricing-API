package com.id.bookshop.exception;

import com.id.bookshop.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * The Class GlobalExceptionHandler is for exception handling.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handle illegal argument exception
     *
     * @param ex the exception
     * @return error response
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException ex) {
        LOGGER.error(ex.getMessage(), ex);
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", ex.getMessage());
    }

    /**
     * Handle empty basket exception
     *
     * @param ex the exception
     * @return error response
     */
    @ExceptionHandler(EmptyBasketException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmptyBasketException(EmptyBasketException ex) {
        LOGGER.error(ex.getMessage(), ex);
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "BAD_BASKET", ex.getMessage());
    }

    /**
     * Handle global exception handler.
     *
     * @param ex the exception
     * @return error response
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneralException(Exception ex) {
        LOGGER.error(ex.getMessage(), ex);
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred");
    }

}
