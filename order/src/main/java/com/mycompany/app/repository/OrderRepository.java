package com.mycompany.app.repository;

import com.mycompany.app.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> getOrdersByProductId(Integer integer);

    List<Order> getOrdersByUserId(Integer userId);
}
