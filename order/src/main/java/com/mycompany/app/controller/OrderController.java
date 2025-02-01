package com.mycompany.app.controller;

import com.mycompany.app.record.OrderRequest;
import com.mycompany.app.record.OrderResponse;
import com.mycompany.app.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody @Valid OrderRequest orderRequest, @RequestHeader("Authorization") String authorization) {
        orderService.saveOrder(orderRequest, authorization);
        return ResponseEntity.ok("Order created");
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrders(@RequestHeader("Authorization") String authorization){
       List<OrderResponse> orders =  orderService.getAllOrders(authorization);
       return ResponseEntity.ok(orders);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUserId(@PathVariable("userId") Integer userId , @RequestHeader("Authorization") String authorization ) {
        List<OrderResponse> orderResponses = orderService.getOrdersByUser(userId, authorization);
        return ResponseEntity.ok(orderResponses);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByProductId(@PathVariable("productId") Integer productId , @RequestHeader("Authorization") String authorization ) {
        List<OrderResponse> orderResponses = orderService.getOrdersByProduct(productId, authorization);
        return ResponseEntity.ok(orderResponses);
    }

}
