package com.dft.api.exception;

public class TooManyRequestsException extends CartWebClientException {
    private static final String DEFAULT_MESSAGE = "The application stopped accepting requests because the rate limit has been exceeded";

    public TooManyRequestsException() {
        super(DEFAULT_MESSAGE);
    }

    public TooManyRequestsException(String message) {
        super(message);
    }
}