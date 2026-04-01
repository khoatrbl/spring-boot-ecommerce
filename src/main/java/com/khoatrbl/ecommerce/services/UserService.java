package com.khoatrbl.ecommerce.services;

import com.khoatrbl.ecommerce.domain.dtos.UpdateUserRequest;
import com.khoatrbl.ecommerce.domain.entities.User;

import java.util.UUID;

public interface UserService {
    User getUserProfile(UUID userId);

    User updateUserProfile(UUID userId, UpdateUserRequest request);
}
