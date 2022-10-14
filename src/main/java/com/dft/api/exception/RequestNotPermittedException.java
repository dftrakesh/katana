package com.dft.api.exception;

public class RequestNotPermittedException extends CartWebClientException {

    private static final String DEFAULT_MESSAGE = "The target resource doesn't support this method.";

    public RequestNotPermittedException() {
        super(DEFAULT_MESSAGE);
    }

    public RequestNotPermittedException(String message) {
        super(message);
    }
}