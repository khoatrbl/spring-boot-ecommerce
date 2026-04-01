package com.khoatrbl.ecommerce.controllers;

import com.khoatrbl.ecommerce.domain.dtos.*;
import com.khoatrbl.ecommerce.domain.entities.User;
import com.khoatrbl.ecommerce.domain.entities.UserRole;
import com.khoatrbl.ecommerce.mappers.UserMapper;
import com.khoatrbl.ecommerce.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    // TODO: Update this endpoint so that it's mapped with the current user obtained through the token
    @GetMapping(path = "/me/profile")
    public ResponseEntity<UserResponse> getUserProfile(@RequestAttribute("userId") UUID userId) {
        User foundUser = userService.getUserProfile(userId);

        UserResponse userResponse = userMapper.toResponse(foundUser);

        return ResponseEntity.ok(userResponse);
    }

    @PutMapping(path = "/me/profile")
    public ResponseEntity<UserResponse> updateUserProfile(
            @RequestAttribute("userId") UUID userId,
            @RequestBody UpdateUserProfileRequestDto updateUserProfileRequestDto) {

        UpdateUserProfileRequest request = userMapper.toUpdateUserProfileRequest(updateUserProfileRequestDto);

        User updatedUser = userService.updateUserProfileAsUser(userId, request);
        UserResponse userResponse = userMapper.toResponse(updatedUser);

        return ResponseEntity.ok(userResponse);
    }

    @GetMapping(path = "/admin/users")
    public ResponseEntity<List<UserResponse>> getAllUsers(@RequestParam(required = false) UserRole role) {
        List<User> users = userService.getAllUsers(role);

        List<UserResponse> response = users.stream().map(userMapper::toResponse).toList();

        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/admin/users/{id}")
    public ResponseEntity<UserResponse> updateUserAsAdmin(
            @PathVariable("id") UUID userId,
            @RequestBody UpdateUserProfileAsAdminRequestDto updateUserProfileAsAdminRequestDto) {

        UpdateUserProfileAsAdminRequest request = userMapper.toUpdateUserProfileAsAdminRequest(updateUserProfileAsAdminRequestDto);

        User updatedUser = userService.updateUserProfileAsAdmin(userId, request);
        UserResponse userResponse = userMapper.toResponse(updatedUser);

        return ResponseEntity.ok(userResponse);

    }

    @DeleteMapping(path = "/admin/users/{id}")
    public ResponseEntity<Void> deleteUserAsAdmin(@PathVariable("id") UUID userId) {
        userService.deleteUserById(userId);

        return ResponseEntity.noContent().build();
    }
}
