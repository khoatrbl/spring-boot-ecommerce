package com.khoatrbl.ecommerce.services;

import com.khoatrbl.ecommerce.domain.dtos.RegisterRequest;
import com.khoatrbl.ecommerce.domain.dtos.UpdateUserRequest;
import com.khoatrbl.ecommerce.domain.entities.User;
import com.khoatrbl.ecommerce.domain.entities.UserRole;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User getUserProfile(UUID userId);

    List<User> getAllUsers(UserRole role);

    User updateUserProfile(UUID userId, UpdateUserRequest request);

    User registerUser(RegisterRequest request);


}
