package com.dft.api.exception;

public class BadRequestException extends CartWebClientException {

    private static final String DEFAULT_MESSAGE = "The request was not understood by the server";

    public BadRequestException() {
        this(DEFAULT_MESSAGE);
    }

    public BadRequestException(String message) {
        super(message);
    }
}