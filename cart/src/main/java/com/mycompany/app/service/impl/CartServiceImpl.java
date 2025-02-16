package com.mycompany.app.service.impl;

import com.mycompany.app.controller.ProductForCart;
import com.mycompany.app.entity.*;
import com.mycompany.app.model.CartResponse;
import com.mycompany.app.model.ProductResponse;
import com.mycompany.app.repository.CartRepository;
import com.mycompany.app.repository.OrderRepository;
import com.mycompany.app.service.CartService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductForCart productForCart;
    private final OrderRepository orderRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductForCart productForCart, OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.productForCart = productForCart;
        this.orderRepository = orderRepository;
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
        Cart cart = cartRepository.findCartByCartStatusAndUserId(CartStatus.IN_PROGRESS, userId).orElse(null);
        if(cart == null) return null;
        List<ProductResponse> productResponses = new ArrayList<>();
        for(Integer productId : cart.getProductIds()) {
            ProductResponse productResponse = productForCart.getProduct(productId, authorization);
            productResponses.add(productResponse);
        }

        return CartResponse.builder()
                .cartId(cart.getId())
                .status(cart.getCartStatus())
                .productResponseList(productResponses) // Properly setting the list
                .userId(userId)
                .build();
    }

    @Override
    public void createOrder(Integer cartId, PaymentMethod paymentMethod) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if(cart == null) {
            throw new RuntimeException("Cart not found");
        }
        if(!cart.getCartStatus().equals(CartStatus.IN_PROGRESS)) {
            throw new RuntimeException("Cart is already used in an order");
        }
        cart.setCartStatus(CartStatus.COMPLETED);
        cartRepository.save(cart);
        Order newOrder = Order.builder()
                .cart(cart)
                .paymentMethod(paymentMethod)
                .status(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
        orderRepository.save(newOrder);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersForUser(Integer userId) {
        List<Order> allOrders = getAllOrders();
        List<Order> userOrders = new ArrayList<>();
        for(Order order : allOrders) {
            Integer userIdFound = order.getCart().getUserId();
            if(userIdFound.equals(userId)) {
                userOrders.add(order);
            }
        }
        return userOrders;
    }

    @Override
    public List<Order> getOrdersForProduct(Integer productId) {
        List<Order> allOrders = getAllOrders();
        List<Order> productOrders = new ArrayList<>();
        for(Order order: allOrders){
            if(order.getCart().getProductIds().contains(productId)) productOrders.add(order);
        }
        return productOrders;
    }

}
