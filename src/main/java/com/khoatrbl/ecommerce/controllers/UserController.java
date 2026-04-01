package com.khoatrbl.ecommerce.controllers;

import com.khoatrbl.ecommerce.domain.dtos.*;
import com.khoatrbl.ecommerce.domain.entities.User;
import com.khoatrbl.ecommerce.domain.entities.UserRole;
import com.khoatrbl.ecommerce.mappers.AuthMapper;
import com.khoatrbl.ecommerce.mappers.UserMapper;
import com.khoatrbl.ecommerce.services.UserService;
import jakarta.validation.Valid;
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
    private final AuthMapper authMapper;

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
            @Valid @RequestBody UpdateUserProfileRequestDto updateUserProfileRequestDto) {

        UpdateUserProfileRequest request = userMapper.toUpdateUserProfileRequest(updateUserProfileRequestDto);

        User updatedUser = userService.updateUserProfileAsUser(userId, request);
        UserResponse userResponse = userMapper.toResponse(updatedUser);

        return ResponseEntity.ok(userResponse);
    }

    @PatchMapping(path = "/me/password")
    public ResponseEntity<UserResponse> changePassword(
            @RequestAttribute("userId") UUID userId,
            @Valid @RequestBody ChangePasswordRequestDto changePasswordRequestDto) {

        ChangePasswordRequest request = authMapper.toChangePasswordRequest(changePasswordRequestDto);

        User updatedUser = userService.changePassword(userId, request);

        UserResponse userResponse = userMapper.toResponse(updatedUser);

        return ResponseEntity.ok(userResponse);
    }


    @GetMapping(path = "/admin/users")
    public ResponseEntity<List<UserResponse>> getAllUsers(@RequestParam(required = false) UserRole role) {
        List<User> users = userService.getAllUsers(role);

        List<UserResponse> response = users.stream().map(userMapper::toResponse).toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/admin/users/{id}")
    public ResponseEntity<UserResponse> getUserProfileByIdAsAdmin(@PathVariable("id") UUID userId) {
        return getUserProfile(userId);
    }

    @PutMapping(path = "/admin/users/{id}")
    public ResponseEntity<UserResponse> updateUserAsAdmin(
            @PathVariable("id") UUID userId,
            @Valid @RequestBody UpdateUserProfileAsAdminRequestDto updateUserProfileAsAdminRequestDto) {

        UpdateUserProfileAsAdminRequest request = userMapper.toUpdateUserProfileAsAdminRequest(updateUserProfileAsAdminRequestDto);

        User updatedUser = userService.updateUserProfileAsAdmin(userId, request);
        UserResponse userResponse = userMapper.toResponse(updatedUser);

        return ResponseEntity.ok(userResponse);

    }

    @PatchMapping(path = "/admin/users/{id}")
    public ResponseEntity<UserResponse> changePasswordAsAdmin(
            @PathVariable("id") UUID userId,
            @Valid @RequestBody ChangePasswordRequestDto changePasswordRequestDto) {

        return changePassword(userId, changePasswordRequestDto);
    }


    @DeleteMapping(path = "/admin/users/{id}")
    public ResponseEntity<Void> deleteUserAsAdmin(@PathVariable("id") UUID userId) {
        userService.deleteUserById(userId);

        return ResponseEntity.noContent().build();
    }
}
