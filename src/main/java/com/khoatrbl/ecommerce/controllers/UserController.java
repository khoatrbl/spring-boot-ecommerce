package com.khoatrbl.ecommerce.controllers;

import com.khoatrbl.ecommerce.domain.dtos.UpdateUserProfileRequestDto;
import com.khoatrbl.ecommerce.domain.dtos.UpdateUserRequest;
import com.khoatrbl.ecommerce.domain.dtos.UserResponse;
import com.khoatrbl.ecommerce.domain.entities.User;
import com.khoatrbl.ecommerce.mappers.UserMapper;
import com.khoatrbl.ecommerce.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/me")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    // TODO: Update this endpoint so that it's mapped with the current user obtained through the token
    @GetMapping(path = "/profile")
    public ResponseEntity<UserResponse> getUserProfile(@RequestAttribute("userId") UUID userId) {
        User foundUser = userService.getUserProfile(userId);

        UserResponse userResponse = userMapper.toResponse(foundUser);

        return ResponseEntity.ok(userResponse);
    }

    @PutMapping(path = "/profile")
    public ResponseEntity<UserResponse> updateUserProfile(
            @RequestAttribute("userId") UUID userId,
            @RequestBody UpdateUserProfileRequestDto updateUserProfileRequestDto) {

        UpdateUserRequest request = userMapper.toRequest(updateUserProfileRequestDto);

        User updatedUser = userService.updateUserProfile(userId, request);
        UserResponse userResponse = userMapper.toResponse(updatedUser);

        return ResponseEntity.ok(userResponse);
    }
}
