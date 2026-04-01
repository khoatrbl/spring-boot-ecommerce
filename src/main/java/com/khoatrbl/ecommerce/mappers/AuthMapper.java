package com.khoatrbl.ecommerce.mappers;

import com.khoatrbl.ecommerce.domain.dtos.*;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthMapper {
    LogInRequest toLogInRequest(LogInRequestDto dto);

    RegisterRequest toRegisterRequest(RegisterRequestDto dto);

    ChangePasswordRequest toChangePasswordRequest(ChangePasswordRequestDto dto);
}
