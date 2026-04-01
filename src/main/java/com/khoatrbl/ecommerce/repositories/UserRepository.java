package com.khoatrbl.ecommerce.repositories;

import com.khoatrbl.ecommerce.domain.entities.User;
import com.khoatrbl.ecommerce.domain.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    List<User> findAllByRole(UserRole role);

    boolean existsByEmail(String email);
}
