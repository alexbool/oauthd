package com.alexbool.oauth.user;

public class LoginAlreadyExistsException extends IllegalArgumentException {

    public LoginAlreadyExistsException(String username) {
        super(String.format("User %s already exists", username));
    }
}
