package com.alexbool.user;

public class UsernameAlreadyExistsException extends IllegalArgumentException {

    public UsernameAlreadyExistsException(String username) {
        super(String.format("User %s already exists", username));
    }
}
