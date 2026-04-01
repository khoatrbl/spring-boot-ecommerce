package com.khoatrbl.ecommerce.services;

import com.khoatrbl.ecommerce.domain.dtos.ChangePasswordRequest;
import com.khoatrbl.ecommerce.domain.dtos.RegisterRequest;
import com.khoatrbl.ecommerce.domain.dtos.UpdateUserProfileAsAdminRequest;
import com.khoatrbl.ecommerce.domain.dtos.UpdateUserProfileRequest;
import com.khoatrbl.ecommerce.domain.entities.User;
import com.khoatrbl.ecommerce.domain.entities.UserRole;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User getUserProfile(UUID userId);

    List<User> getAllUsers(UserRole role);

    User updateUserProfileAsUser(UUID userId, UpdateUserProfileRequest request);

    User updateUserProfileAsAdmin(UUID userId, UpdateUserProfileAsAdminRequest request);

    void deleteUserById(UUID userId);

    User registerUser(RegisterRequest request);

    User changePassword(UUID userId, ChangePasswordRequest request);


}
