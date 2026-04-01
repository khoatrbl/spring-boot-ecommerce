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
public class ChangePasswordRequestDto {
    @NotBlank(message = "Old password is required!")
    private String oldPassword;

    @NotBlank(message = "New password is required!")
    @Size(min = 8, message = "Password must be longer than {min} characters!")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "Password must include at least 1 uppercase letter, 1 number and 1 special character!")
    private String newPassword;

    @NotBlank(message = "Confirm password is required!")
    private String confirmedPassword;
}
