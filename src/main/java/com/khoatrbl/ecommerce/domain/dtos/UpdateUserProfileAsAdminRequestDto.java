package com.khoatrbl.ecommerce.domain.dtos;

import com.khoatrbl.ecommerce.domain.entities.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserProfileAsAdminRequestDto {
    @NotBlank(message = "Display name is required!")
    @Size(min = 3, max = 40, message = "Display name must be between {min} and {max} characters")
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "Display name must only contain alphabetical letters and whitespaces!")
    private String displayName;

    @NotBlank(message = "Street is required!")
    private String street;

    @NotNull(message = "Role is required!")
    private UserRole role;

    @NotBlank(message = "Ward is required!")
    private String ward;

    @NotBlank(message = "City is required!")
    private String city;
}
