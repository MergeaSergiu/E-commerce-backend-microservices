package com.mycompany.app.service;

import com.mycompany.app.entity.Cart;
import com.mycompany.app.entity.Order;
import com.mycompany.app.entity.PaymentMethod;
import com.mycompany.app.model.CartResponse;

import java.util.List;

public interface CartService {

    Cart getCart(Integer userId);

    void addToCart(String authorization, Integer userId, Integer productId);

    CartResponse getCartByUserIdInProgress(String authorization, Integer userId);

    void createOrder(Integer cartId, PaymentMethod paymentMethod);

    List<Order> getAllOrders();

    List<Order> getOrdersForUser(Integer userId);

    List<Order> getOrdersForProduct(Integer productId);
}
