package com.khoatrbl.ecommerce.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderRequestDto {
    @NotBlank(message = "Recipient name is required!")
    private String recipientName;

    @NotBlank(message = "Phone number is required!")
    private String phoneNumber;

    @NotBlank(message = "Shipping address is required!")
    private String shippingAddress;



}
