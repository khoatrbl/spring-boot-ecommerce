package com.khoatrbl.ecommerce.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequest {
    private String displayName;
    private String street;
    private String ward;
    private String city;
}
