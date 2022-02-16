package com.estore.ecomerce.controller;

import com.estore.ecomerce.dto.UserRegisterRequest;
import com.estore.ecomerce.dto.UserRegisterResponse;
import com.estore.ecomerce.service.IRegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private IRegisterUserService registerUserService;

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> register(@Valid @RequestBody UserRegisterRequest request) {
        UserRegisterResponse userRegisterResponse = registerUserService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userRegisterResponse);
    }
}
