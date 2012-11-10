package com.alexbool.user.web;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.util.Assert;

public class RegisterUserCommand {

    private final String username;
    private final String password;

    @JsonCreator
    public RegisterUserCommand(
            @JsonProperty("username") String username,
            @JsonProperty("password") String password)
    {
        Assert.hasText(username, "Username must not be blank");
        Assert.hasText(password, "Password must not be blank");
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
