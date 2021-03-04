package com.oneworldaccuracy.onboardingservice.controller;

import com.oneworldaccuracy.onboardingservice.service.UserService;
import com.oneworldaccuracy.onboardingservice.vo.request.RegisterRequest;
import com.oneworldaccuracy.onboardingservice.vo.request.UpdateRequest;
import com.oneworldaccuracy.onboardingservice.vo.response.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

@RestController
public class OnBoardingOperation {

    @Autowired
    UserService service;

    @PostMapping(
            value = "/user",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Async
    public CompletableFuture<ServiceResponse> registerUser(@RequestBody RegisterRequest request, HttpServletRequest req) {
        return CompletableFuture.completedFuture(service.registerUser(request, req));
    }

    @GetMapping(
            value = "/users/{pageNumber}/{pageSize}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Async
    public CompletableFuture<ServiceResponse> getRegisteredUsers(@PathVariable("pageNumber") int pageNumber,
                                                                 @PathVariable("pageSize") int pageSize) {
        return CompletableFuture.completedFuture(service.getRegisteredUsers(pageNumber, pageSize));
    }

    @PutMapping(
            value = "/user/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Async
    public CompletableFuture<ServiceResponse> updateUser(@PathVariable("id") long userId,
                                                         @RequestBody UpdateRequest request) {
        return CompletableFuture.completedFuture(service.updateUser(userId, request));
    }


    @DeleteMapping(
            value = "/user/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Async
    public CompletableFuture<ServiceResponse> deactivateUser(@PathVariable("id") long userId) {
        return CompletableFuture.completedFuture(service.deactivateUser(userId));
    }

    @GetMapping(
            value = "/verify",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Async
    public CompletableFuture<ServiceResponse> verifyEmail(@RequestParam("code") String code) {
        return CompletableFuture.completedFuture(service.verifyUser(code));
    }

}
