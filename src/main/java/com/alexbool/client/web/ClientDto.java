package com.alexbool.client.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.springframework.security.core.GrantedAuthority;

@JsonPropertyOrder("client_id")
public class ClientDto {

    private final String clientId;
    private final List<String> authorities;

    public ClientDto(String clientId, Collection<GrantedAuthority> authorities) {
        this.clientId = clientId;
        List<String> stringAuthorities = new ArrayList<>();
        for (GrantedAuthority a : authorities) {
            stringAuthorities.add(a.getAuthority());
        }
        this.authorities = Collections.unmodifiableList(stringAuthorities);
    }

    @JsonProperty("client_id")
    public String getClientId() {
        return clientId;
    }

    public List<String> getAuthorities() {
        return authorities;
    }
}
