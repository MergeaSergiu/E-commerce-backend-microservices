package com.mycompany.app.service.impl;

import com.mycompany.app.controller.ProductAndUserForOrder;
import com.mycompany.app.model.Order;
import com.mycompany.app.record.*;
import com.mycompany.app.repository.OrderRepository;
import com.mycompany.app.service.OrderService;
import com.mycompany.app.service.RabbitMQProducer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductAndUserForOrder productAndUserForOrder;
    private final RabbitMQProducer rabbitMQProducer;

    public OrderServiceImpl(OrderRepository orderRepository,  ProductAndUserForOrder productAndUserForOrder, RabbitMQProducer rabbitMQProducer) {
        this.orderRepository = orderRepository;
        this.productAndUserForOrder = productAndUserForOrder;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @Override
    public void saveOrder(OrderRequest orderRequest, String authorization) {
        ProductResponse productResponse = productAndUserForOrder.getProduct(orderRequest.productId(), authorization);
        UserResponse userResponse = productAndUserForOrder.getUser(orderRequest.userId());
        List<Order> orders = orderRepository.getOrdersByProductId(orderRequest.productId());
        if(orders.size() > productResponse.quantity()) {
            System.out.println("There are more orders than products in inventory");
        }
        Order order = Order.builder()
                .userId(orderRequest.userId())
                .productId(orderRequest.productId())
                .createdAt(LocalDateTime.now())
                .paymentMethod(orderRequest.paymentMethod())
                .build();
        orderRepository.save(order);
        OrderMessageDTO orderMessageDTO = OrderMessageDTO.builder()
                        .email(userResponse.username())
                                .message("Your order was saved: " + "\nUser:" + userResponse.username() + "\nProduct:" + productResponse.name() + "\n Price: " + productResponse.price() + "\n Payment Method: " + orderRequest.paymentMethod())
                                    .build();
        rabbitMQProducer.sendMessage(orderMessageDTO);
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

    @Override
    public List<OrderResponse> getOrdersByUser(Integer userId, String authorization) {
        List<Order> ordersForUser = orderRepository.getOrdersByUserId(userId);
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order order : ordersForUser) {
            UserResponse userResponse = productAndUserForOrder.getUser(userId);
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

    @Override
    public List<OrderResponse> getOrdersByProduct(Integer productId, String authorization) {
        List<Order> orders = orderRepository.getOrdersByProductId(productId);
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order order : orders) {
            ProductResponse productResponse = productAndUserForOrder.getProduct(order.getProductId(), authorization);
            OrderResponse orderResponse = OrderResponse.builder()
                    .orderId(order.getId())
                    .userId(null)
                    .productId(productResponse.productId())
                    .username(null)
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
