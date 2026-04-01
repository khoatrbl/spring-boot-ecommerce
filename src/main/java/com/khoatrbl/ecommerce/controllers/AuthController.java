package com.khoatrbl.ecommerce.controllers;

import com.khoatrbl.ecommerce.domain.dtos.*;
import com.khoatrbl.ecommerce.domain.entities.User;
import com.khoatrbl.ecommerce.mappers.AuthMapper;
import com.khoatrbl.ecommerce.mappers.UserMapper;
import com.khoatrbl.ecommerce.services.AuthenticationService;
import com.khoatrbl.ecommerce.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final AuthMapper authMapper;
    private final UserMapper userMapper;

    @PostMapping(path = "/login")
    public ResponseEntity<LogInResponse> logIn(@RequestBody LogInRequestDto logInRequestDto) {
        LogInRequest request = authMapper.toLogInRequest(logInRequestDto);

        UserDetails userDetails = authenticationService
                .authenticate(request);


        String token = authenticationService.generateToken(userDetails);

        LogInResponse logInResponse = LogInResponse.builder()
                .token(token)
                .expiresIn(8600L)
                .build();

        return ResponseEntity.ok(logInResponse);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequestDto registerRequestDto) {
        RegisterRequest request = authMapper.toRegisterRequest(registerRequestDto);

        User newUser = userService.registerUser(request);
        UserResponse userResponse = userMapper.toResponse(newUser);

        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }
}
