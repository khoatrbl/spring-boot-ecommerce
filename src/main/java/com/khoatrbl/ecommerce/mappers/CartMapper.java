package com.khoatrbl.ecommerce.mappers;

import com.khoatrbl.ecommerce.domain.dtos.*;
import com.khoatrbl.ecommerce.domain.entities.Cart;
import com.khoatrbl.ecommerce.domain.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartMapper {
    @Mapping(source = ".", target = "totalAmount", qualifiedByName = "calculateTotalAmount")
    @Mapping(source = ".", target = "totalNumberOfItems", qualifiedByName = "calculateTotalNumberOfItems")
    CartResponse toCartResponse(Cart cart);

    AddCartItemRequest toAddCartItemRequest(AddCartItemRequestDto dto);

    UpdateCartItemRequest toUpdateCartItemRequest(UpdateCartItemRequestDto dto);

    @Named("calculateTotalAmount")
    default BigDecimal calculateTotalAmount(Cart cart) {
        return cart.calculateTotalAmount();
    }

    @Named("calculateTotalNumberOfItems")
    default int calculateTotalNumberOfItems(Cart cart) {
        return cart.calculateTotalNumberOfItem();
    }

}
