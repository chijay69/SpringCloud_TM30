package org.example.service;


import org.example.dto.OrderRequestDto;
import org.example.entity.Order;

import java.util.List;

public interface OrderService {

    List<Order> allOrders();
    Order createOrder(OrderRequestDto orderRequestDto);
}
