package com.mycompany.app.service.impl;

import com.mycompany.app.controller.ProductAndUserForOrder;
import com.mycompany.app.model.Order;
import com.mycompany.app.record.OrderRequest;
import com.mycompany.app.record.OrderResponse;
import com.mycompany.app.record.ProductResponse;
import com.mycompany.app.record.UserResponse;
import com.mycompany.app.repository.OrderRepository;
import com.mycompany.app.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductAndUserForOrder productAndUserForOrder;

    public OrderServiceImpl(OrderRepository orderRepository,  ProductAndUserForOrder productAndUserForOrder) {
        this.orderRepository = orderRepository;
        this.productAndUserForOrder = productAndUserForOrder;
    }

    @Override
    public void saveOrder(OrderRequest orderRequest) {

        Order order = Order.builder()
                .userId(orderRequest.userId())
                .productId(orderRequest.productId())
                .createdAt(LocalDateTime.now())
                .paymentMethod(orderRequest.paymentMethod())
                .build();

        orderRepository.save(order);
    }

    @Override
    public List<OrderResponse> getAllOrders(String authorization) {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order order : orders) {
            UserResponse userResponse = productAndUserForOrder.getUser(order.getUserId());
            ProductResponse productResponse = productAndUserForOrder.getProduct(order.getProductId(), authorization);
            OrderResponse orderResponse = OrderResponse.builder()
                    .orderId(order.getId())
                    .userId(userResponse.userId())
                    .productId(productResponse.productId())
                    .username(userResponse.username())
                    .productName(productResponse.name())
                    .productPrice(productResponse.price())
                    .orderDate(order.getCreatedAt())
                    .paymentMethod(order.getPaymentMethod())
                    .build();
            orderResponses.add(orderResponse);
        }
        return orderResponses;
    }
}
