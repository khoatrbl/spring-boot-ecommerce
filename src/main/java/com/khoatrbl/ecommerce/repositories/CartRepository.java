package com.khoatrbl.ecommerce.repositories;

import com.khoatrbl.ecommerce.domain.entities.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartRepository extends CrudRepository<Cart, UUID> {
}
