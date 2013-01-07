package com.alexbool.oauth.user.web;

import java.util.Arrays;
import java.util.UUID;

import com.google.common.base.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
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
        return new UserInfo(user.getUid(), user.getLogin(), user.getAuthorities());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void changePassword(@RequestBody ChangePasswordCommand cmd, User user) {
        Assert.isTrue(user.getLogin().isPresent(), "Only users with login can update his/her password");
        userRepository.updatePassword(user.getLogin().get(), cmd.getPassword());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(User user) {
        Assert.isTrue(user.getLogin().isPresent(), "Only users with login can delete his/her account");
        userRepository.delete(user.getLogin().get());
    }
}
