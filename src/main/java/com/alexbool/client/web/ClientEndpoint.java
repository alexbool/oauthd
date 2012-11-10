package com.alexbool.client.web;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.BaseClientDetails;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.alexbool.util.StatusCodeException;

@RequestMapping(value = "client", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientEndpoint {

    @Autowired
    private ClientRegistrationService clientRegistrationService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void register(@RequestBody RegisterClientCommand cmd) {
        BaseClientDetails client = new BaseClientDetails(cmd.getClientId(), "", "",
                "authorization_code,refresh_token,implicit,password", "client_general");
        try {
            clientRegistrationService.addClientDetails(client);
        } catch (ClientAlreadyExistsException ex) {
            throw new StatusCodeException(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(Principal principal) {
        try {
            clientRegistrationService.removeClientDetails(principal.getName());
        } catch (NoSuchClientException ex) {
        }
    }
}
