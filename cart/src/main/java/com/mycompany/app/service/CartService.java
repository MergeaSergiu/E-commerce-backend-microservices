package com.mycompany.app.service;

import com.mycompany.app.entity.Cart;
import com.mycompany.app.entity.Order;
import com.mycompany.app.entity.PaymentMethod;
import com.mycompany.app.model.CartResponse;

import java.util.List;

public interface CartService {

    Cart getCart(Integer userId);

    void addToCart(String authorization, Integer productId);

    CartResponse getCartByUserIdInProgress(String authorization);

    void createOrder(String authorization ,Integer cartId, PaymentMethod paymentMethod);

    List<Order> getAllOrders();

    List<Order> getOrdersForUser(String authorization);

    List<Order> getOrdersForProduct(Integer productId);
}
