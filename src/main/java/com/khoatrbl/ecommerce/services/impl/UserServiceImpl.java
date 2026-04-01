package com.khoatrbl.ecommerce.services.impl;

import com.khoatrbl.ecommerce.domain.dtos.RegisterRequest;
import com.khoatrbl.ecommerce.domain.dtos.UpdateUserProfileAsAdminRequest;
import com.khoatrbl.ecommerce.domain.dtos.UpdateUserProfileRequest;
import com.khoatrbl.ecommerce.domain.entities.User;
import com.khoatrbl.ecommerce.domain.entities.UserRole;
import com.khoatrbl.ecommerce.repositories.UserRepository;
import com.khoatrbl.ecommerce.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserProfile(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User does not exist with id: " + userId));
    }

    @Override
    public List<User> getAllUsers(UserRole role) {
        if (role != null) {
            return userRepository.findAllByRole(role);
        }
        return userRepository.findAll();
    }

    @Override
    public User updateUserProfileAsUser(UUID userId, UpdateUserProfileRequest request) {
        return updateUserProfile(
                userId,
                request.getDisplayName(),
                request.getStreet(),
                request.getWard(),
                request.getCity(),
                null,
                false);
    }

    @Override
    public User updateUserProfileAsAdmin(UUID userId, UpdateUserProfileAsAdminRequest request) {
        return updateUserProfile(
                userId,
                request.getDisplayName(),
                request.getStreet(),
                request.getWard(),
                request.getCity(),
                request.getRole(),
                true);
    }

    private User updateUserProfile(UUID userId,
                                   String displayName,
                                   String street,
                                   String ward,
                                   String city,
                                   UserRole role,
                                   boolean canChangeRole) {

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User does not exist with id: " + userId));

        existingUser.setDisplayName(displayName);
        existingUser.setStreet(street);
        existingUser.setWard(ward);
        existingUser.setCity(city);

        if (canChangeRole && role != null) {
            existingUser.setRole(role);
        }

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUserById(UUID userId) {
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional
    public User registerUser(RegisterRequest request) {
        String requestEmail = request.getEmail();
        String requestPassword = request.getPassword();
        String requestConfirmedPassword = request.getConfirmedPassword();

        if (userRepository.existsByEmail(requestEmail)) {
            throw new IllegalArgumentException("Email already exists!");
        }

        if (!requestPassword.equals(requestConfirmedPassword)) {
            throw new IllegalArgumentException("Passwords do not match!");
        }

        String encodedPassword = encodePassword(requestPassword);

        User newUser = User.builder()
                .email(requestEmail)
                .password(encodedPassword)
                .displayName(request.getDisplayName())
                .role(UserRole.USER)
                .street(request.getStreet())
                .ward(request.getWard())
                .city(request.getCity())
                .build();

        return userRepository.save(newUser);

    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
