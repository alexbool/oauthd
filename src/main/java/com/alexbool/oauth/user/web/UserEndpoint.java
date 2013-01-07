package com.alexbool.oauth.user.web;

import java.security.Principal;
import java.util.Arrays;
import java.util.UUID;

import com.google.common.base.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.alexbool.oauth.user.User;
import com.alexbool.oauth.user.UserRepository;

@RequestMapping(value = "user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserEndpoint {

    @Autowired
    private UserRepository userRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void register(@RequestBody RegisterUserCommand cmd) {
        userRepository.save(
                new User(UUID.randomUUID(), Optional.of(cmd.getUsername()), cmd.getPassword(), false,
                        Arrays.asList("user_general")));
    }

    @RequestMapping(value = "check-username-free", method = RequestMethod.GET)
    public @ResponseBody CheckResult checkUsernameFree(
            @RequestParam(required = true) String username)
    {
        return new CheckResult(!userRepository.exists(username));
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody UserInfo userInfo(User user) {
        return new UserInfo(user.getUid(), user.getUsername(), user.getAuthorities());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void changePassword(@RequestBody ChangePasswordCommand cmd, Principal principal) {
        userRepository.updatePassword(principal.getName(), cmd.getPassword());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(Principal principal) {
        userRepository.delete(principal.getName());
    }
}
