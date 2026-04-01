package com.khoatrbl.ecommerce.services.impl;

import com.khoatrbl.ecommerce.domain.dtos.UpdateUserRequest;
import com.khoatrbl.ecommerce.domain.entities.User;
import com.khoatrbl.ecommerce.repositories.UserRepository;
import com.khoatrbl.ecommerce.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getUserProfile(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User does not exist with id: " + userId));
    }

    @Override
    public User updateUserProfile(UUID userId, UpdateUserRequest request) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User does not exist with id: " + userId));

        existingUser.setDisplayName(request.getDisplayName());
        existingUser.setStreet(request.getStreet());
        existingUser.setWard(request.getWard());
        existingUser.setCity(request.getCity());

        return userRepository.save(existingUser);
    }
}
