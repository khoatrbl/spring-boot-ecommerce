package com.khoatrbl.ecommerce.domain.dtos;

import com.khoatrbl.ecommerce.domain.entities.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String displayName;
    private UserRole role;
    private String street;
    private String ward;
    private String city;
}
