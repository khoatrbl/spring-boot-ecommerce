package com.khoatrbl.ecommerce.mappers;

import com.khoatrbl.ecommerce.domain.dtos.AddCartItemRequest;
import com.khoatrbl.ecommerce.domain.dtos.AddCartItemRequestDto;
import com.khoatrbl.ecommerce.domain.dtos.CartResponse;
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
    @Mapping(source = "items", target = "totalAmount", qualifiedByName = "calculateTotalAmount")
    @Mapping(source = "items", target = "totalNumberOfItems", qualifiedByName = "calculateTotalNumberOfItems")
    CartResponse toCartResponse(Cart cart);

    AddCartItemRequest toAddCartItemRequest(AddCartItemRequestDto dto);

    @Named("calculateTotalAmount")
    default BigDecimal calculateTotalAmount(Map<UUID, CartItem> items) {
        BigDecimal total = BigDecimal.valueOf(0);

        for (UUID productId : items.keySet()) {
            CartItem item = items.get(productId);
            total = item.calculateSubTotal().add(total);
        }

        return total;
    }

    @Named("calculateTotalNumberOfItems")
    default int calculateTotalNumberOfItems(Map<UUID, CartItem> items) {
        return items.size();
    }

}
