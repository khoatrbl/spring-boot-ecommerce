package com.khoatrbl.ecommerce.services;

import com.khoatrbl.ecommerce.domain.dtos.LogInRequest;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
    UserDetails authenticate(LogInRequest request);

    String generateToken(UserDetails userDetails);

    UserDetails validateToken(String token);
}
