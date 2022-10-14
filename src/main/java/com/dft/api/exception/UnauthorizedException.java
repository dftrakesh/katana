package com.dft.api.exception;

public class UnauthorizedException extends CartWebClientException {
    private static final String DEFAULT_MESSAGE = "The authentication credentials provided are incorrect or the oauth token has expired";

    public UnauthorizedException() {
        super(DEFAULT_MESSAGE);
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}