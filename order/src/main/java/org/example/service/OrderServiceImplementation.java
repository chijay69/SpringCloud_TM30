package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.OrderRequestDto;
import org.example.entity.Order;
import org.example.entity.Product;
import org.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImplementation implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> allOrders() {
        try {

            List<Order> orders = orderRepository.findAll();
            return orders;
        } catch (Exception exception) {
            log.error(exception.getLocalizedMessage(), exception);
            throw  new RuntimeException("Unable to retrieve orders", exception);
        }
    }

    @Override
    public Order createOrder(OrderRequestDto orderRequestDto) {
        try {
            Order some_order = orderRepository.findById(orderRequestDto.getOrderId());
            assert (some_order == null);
            Order order = new Order();
            order.setId(orderRequestDto.getOrderId());
            order.setQuantity(orderRequestDto.getQuantity());
            order.setProductId(orderRequestDto.getProductId());
            Order order1 = orderRepository.save(order);
            return order1;
        }catch (Exception e){
            log.error(" Error occurred trying to save order", e);
            throw new RuntimeException( e.getLocalizedMessage());
        }
    }
}
