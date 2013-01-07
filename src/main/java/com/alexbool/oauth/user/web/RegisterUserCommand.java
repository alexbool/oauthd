package com.alexbool.oauth.user.web;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.util.Assert;

public class RegisterUserCommand {

    private final String login;
    private final String password;

    @JsonCreator
    public RegisterUserCommand(
            @JsonProperty("login") String login,
            @JsonProperty("password") String password)
    {
        Assert.hasText(login, "Login must not be blank");
        Assert.hasText(password, "Password must not be blank");
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
