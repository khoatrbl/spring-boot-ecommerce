package com.khoatrbl.ecommerce.mappers;

import com.khoatrbl.ecommerce.domain.dtos.CreateOrderRequest;
import com.khoatrbl.ecommerce.domain.dtos.CreateOrderRequestDto;
import com.khoatrbl.ecommerce.domain.dtos.OrderResponse;
import com.khoatrbl.ecommerce.domain.entities.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    OrderResponse toOrderResponse(Orders orders);

    CreateOrderRequest toCreateOrderRequest(CreateOrderRequestDto dto);
}
