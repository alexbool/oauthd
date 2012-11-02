package com.greenpas.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.greenpas.user.UserRepository;

@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserEndpoint {

    @Autowired
    private UserRepository userRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void register(@RequestBody RegisterUserCommand cmd) {
        // TODO
    }

    @RequestMapping(value = "check-username-free", method = RequestMethod.GET)
    public @ResponseBody CheckResult checkUsernameFree(
            @RequestParam(required = true) String username)
    {
        return new CheckResult(userRepository.exists(username));
    }
}
