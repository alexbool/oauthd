package com.alexbool.user.web;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.util.Assert;

public class ChangePasswordCommand {

    private final String password;

    @JsonCreator
    public ChangePasswordCommand(@JsonProperty("password") String password) {
        Assert.hasText(password, "Password must not be blank");
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
