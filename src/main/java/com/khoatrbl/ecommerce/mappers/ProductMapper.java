package com.khoatrbl.ecommerce.mappers;

import com.khoatrbl.ecommerce.domain.dtos.*;
import com.khoatrbl.ecommerce.domain.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    ProductResponse toDto(Product product);

    AddProductRequest toAddProductRequest(AddProductRequestDto dto);

    UpdateProductRequest toUpdateProductRequest(UpdateProductRequestDto dto);
}
