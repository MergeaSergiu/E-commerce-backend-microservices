package com.mycompany.app.service.impl;

import com.mycompany.app.controller.ProductForCart;
import com.mycompany.app.entity.Cart;
import com.mycompany.app.entity.CartStatus;
import com.mycompany.app.model.CartResponse;
import com.mycompany.app.model.ProductResponse;
import com.mycompany.app.repository.CartRepository;
import com.mycompany.app.service.CartService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductForCart productForCart;

    public CartServiceImpl(CartRepository cartRepository, ProductForCart productForCart) {
        this.cartRepository = cartRepository;
        this.productForCart = productForCart;
    }

    @Override
    public Cart getCart(Integer userId) {
        return cartRepository.findCartByCartStatusAndUserId(CartStatus.IN_PROGRESS, userId)
                .orElseGet(() -> Cart.builder()
                        .userId(userId)
                        .cartStatus(CartStatus.IN_PROGRESS)
                        .productIds(new ArrayList<>())
                        .build());
    }

    @Override
    public void addToCart(String authorization, Integer userId, Integer productId) {
        Cart cart = getCart(userId);
        cart.getProductIds().add(productId);
        cartRepository.save(cart);
    }

    @Override
    public CartResponse getCartByUserIdInProgress(String authorization, Integer userId) {
        Cart cart = getCart(userId);
        CartResponse cartResponse = CartResponse.builder()
                .cartId(cart.getId())
                .status(cart.getCartStatus())
                .productResponseList(new ArrayList<>())
                .userId(userId)
                .build();
        for(Integer productId : cart.getProductIds()) {
            ProductResponse productResponse = productForCart.getProduct(productId, authorization);
            cartResponse.productResponseList().add(productResponse);
        }
        return cartResponse;
    }

}
