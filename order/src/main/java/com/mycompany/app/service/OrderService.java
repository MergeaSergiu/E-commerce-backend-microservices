package com.mycompany.app.service;

import com.mycompany.app.record.OrderRequest;
import com.mycompany.app.record.OrderResponse;

import java.util.List;

public interface OrderService {

    void saveOrder(OrderRequest orderRequest);

    List<OrderResponse> getAllOrders(String authorization);
}
