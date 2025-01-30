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
    public ResponseEntity<String> createOrder(@RequestBody @Valid OrderRequest orderRequest) {
        orderService.saveOrder(orderRequest);
        return ResponseEntity.ok("Order created");
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrders(@RequestHeader("Authorization") String authorization){
       List<OrderResponse> orders =  orderService.getAllOrders(authorization);
       return ResponseEntity.ok(orders);
    }
}
