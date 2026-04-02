package com.khoatrbl.ecommerce.domain.entities;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CartItem implements Serializable {
    private UUID productId;
    private int quantity;
    private String productName;
    private BigDecimal price;

    public BigDecimal calculateSubTotal() {
        if (price == null) {
            return BigDecimal.valueOf(0);
        }

        return price.multiply(BigDecimal.valueOf(quantity));
    }
}
