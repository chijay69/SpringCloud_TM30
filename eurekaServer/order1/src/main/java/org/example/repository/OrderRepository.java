package org.example.repository;

import org.example.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository {
    private final Map<Long, Order> orderStore = new HashMap<>();

    public Order save(Order order) {
        if(order == null){
            throw new RuntimeException("Order is null");
        }
        orderStore.put(order.getId(), order);
        return order;
    }

    public List<Order> findAll() {
        return new ArrayList<>(orderStore.values());
    }
}
