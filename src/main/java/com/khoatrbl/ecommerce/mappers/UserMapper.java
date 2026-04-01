package com.khoatrbl.ecommerce.mappers;

import com.khoatrbl.ecommerce.domain.dtos.UpdateUserProfileRequestDto;
import com.khoatrbl.ecommerce.domain.dtos.UpdateUserRequest;
import com.khoatrbl.ecommerce.domain.dtos.UserResponse;
import com.khoatrbl.ecommerce.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserResponse toResponse(User user);

    UpdateUserRequest toRequest(UpdateUserProfileRequestDto dto);
}
