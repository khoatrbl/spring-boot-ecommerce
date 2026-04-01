package com.khoatrbl.ecommerce.mappers;

import com.khoatrbl.ecommerce.domain.dtos.LogInRequest;
import com.khoatrbl.ecommerce.domain.dtos.LogInRequestDto;
import com.khoatrbl.ecommerce.domain.dtos.RegisterRequest;
import com.khoatrbl.ecommerce.domain.dtos.RegisterRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthMapper {
    LogInRequest toLogInRequest(LogInRequestDto dto);

    RegisterRequest toRegisterRequest(RegisterRequestDto dto);
}
