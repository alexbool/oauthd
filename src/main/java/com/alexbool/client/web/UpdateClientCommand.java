package com.alexbool.client.web;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

public class UpdateClientCommand {

    private final String clientSecret;

    @JsonCreator
    public UpdateClientCommand(@JsonProperty("client_secret") String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}
