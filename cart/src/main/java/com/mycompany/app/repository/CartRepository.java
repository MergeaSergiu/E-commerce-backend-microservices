package com.mycompany.app.repository;

import com.mycompany.app.entity.Cart;
import com.mycompany.app.entity.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    Optional<Cart> findCartByCartStatusAndUserId(CartStatus cartStatus, Integer userId);
}
