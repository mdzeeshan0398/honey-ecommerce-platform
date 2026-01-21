package com.honey.app.repository;

import com.honey.app.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,String> {
    Optional<Cart> findByUserId(String userId);
    void deleteByUserId(String userId);
}
