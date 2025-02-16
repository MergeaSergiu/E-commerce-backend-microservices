package com.mycompany.app.service;

import com.mycompany.app.entity.Cart;
import com.mycompany.app.model.CartResponse;

public interface CartService {

    Cart getCart(Integer userId);

    void addToCart(String authorization, Integer userId, Integer productId);

    CartResponse getCartByUserIdInProgress(String authorization, Integer userId);
}
