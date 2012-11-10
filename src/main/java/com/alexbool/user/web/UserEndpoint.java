package com.alexbool.user.web;

import java.security.Principal;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.alexbool.user.User;
import com.alexbool.user.UserRepository;

@RequestMapping(value = "user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserEndpoint {

    @Autowired
    private UserRepository userRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void register(@RequestBody RegisterUserCommand cmd) {
        userRepository.save(
                new User(cmd.getUsername(), cmd.getPassword(), false, Arrays.asList(new String[] { "ROLE_USER" })));
    }

    @RequestMapping(value = "check-username-free", method = RequestMethod.GET)
    public @ResponseBody CheckResult checkUsernameFree(
            @RequestParam(required = true) String username)
    {
        return new CheckResult(!userRepository.exists(username));
    }

    @RequestMapping(value = "info", method = RequestMethod.GET)
    public @ResponseBody UserInfo userInfo(Principal principal) {
        Authentication auth = (Authentication) principal;
        return new UserInfo(auth.getName(), auth.getAuthorities());
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "password", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void changePassword(@RequestBody ChangePasswordCommand cmd, Principal principal) {
        userRepository.updatePassword(principal.getName(), cmd.getPassword());
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(Principal principal) {
        userRepository.delete(principal.getName());
    }
}
