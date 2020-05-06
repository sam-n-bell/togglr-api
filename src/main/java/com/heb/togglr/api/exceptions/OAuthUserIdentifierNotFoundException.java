package com.heb.togglr.api.exceptions;

public class OAuthUserIdentifierNotFoundException extends Exception {

    public OAuthUserIdentifierNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
