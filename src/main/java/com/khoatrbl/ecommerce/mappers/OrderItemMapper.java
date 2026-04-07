package com.khoatrbl.ecommerce.mappers;

import com.khoatrbl.ecommerce.domain.dtos.OrderItemResponse;
import com.khoatrbl.ecommerce.domain.entities.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderItemMapper {
    @Mapping(source = "orders.orderId", target = "orderId")
    @Mapping(source = ".", target = "subTotal", qualifiedByName = "calculateSubtotal")
    OrderItemResponse toOrderItemResponse(OrderItem item);

    @Named("calculateSubtotal")
    default BigDecimal calculateSubtotal(OrderItem item) {
        return item.calculateSubTotal();
    }

}
