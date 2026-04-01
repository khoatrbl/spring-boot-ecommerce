package com.khoatrbl.ecommerce.mappers;

import com.khoatrbl.ecommerce.domain.dtos.*;
import com.khoatrbl.ecommerce.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserResponse toResponse(User user);

    UpdateUserProfileRequest toUpdateUserProfileRequest(UpdateUserProfileRequestDto dto);

    UpdateUserProfileAsAdminRequest toUpdateUserProfileAsAdminRequest(UpdateUserProfileAsAdminRequestDto dto);
}
