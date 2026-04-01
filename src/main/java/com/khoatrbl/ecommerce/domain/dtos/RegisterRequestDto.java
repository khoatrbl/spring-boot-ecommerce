package com.khoatrbl.ecommerce.domain.dtos;

import jakarta.validation.constraints.NotBlank;
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
public class RegisterRequestDto {
    @NotBlank(message = "Email is required!")
    @Pattern(regexp = "^[a-zA-z@._]*$", message = "Email must be valid!")
    private String email;

    @NotBlank(message = "Password is required!")
    @Size(min = 8, message = "Password must be longer than {min} characters!")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "Password must include at least 1 uppercase letter, 1 number and 1 special character!")
    private String password;

    @NotBlank(message = "Password is required!")
    @Size(min = 8, message = "Password must be longer than {min} characters!")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "Password must include at least 1 uppercase letter, 1 number and 1 special character!")
    private String confirmedPassword;

    @NotBlank(message = "Display name is required")
    @Size(min = 3, max = 40, message = "Display name must be between {min} and {max} characters")
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "Display name must only contain alphabetical letters and whitespaces!")
    private String displayName;

    @NotBlank(message = "Street is required!")
    private String street;

    @NotBlank(message = "Ward is required!")
    private String ward;

    @NotBlank(message = "City is required!")
    private String city;
}
