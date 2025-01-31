package com.mycompany.app.service;

import com.mycompany.app.record.OrderRequest;
import com.mycompany.app.record.OrderResponse;

import java.util.List;

public interface OrderService {

    void saveOrder(OrderRequest orderRequest, String authorization);

    List<OrderResponse> getAllOrders(String authorization);

    List<OrderResponse> getOrdersByUser(Integer userId, String authorization);
}
