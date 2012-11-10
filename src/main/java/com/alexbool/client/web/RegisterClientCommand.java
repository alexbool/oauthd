package com.alexbool.client.web;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.util.Assert;

public class RegisterClientCommand {

    private final String clientId;
    private final String clientSecret;

    @JsonCreator
    public RegisterClientCommand(
            @JsonProperty("client_id") String clientId,
            @JsonProperty("client_secret") String clientSecret)
    {
        Assert.hasText(clientId, "Client ID must not be blank");
        Assert.hasText(clientSecret, "Client secret must not be blank");
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}
