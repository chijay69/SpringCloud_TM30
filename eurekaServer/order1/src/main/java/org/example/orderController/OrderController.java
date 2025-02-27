package org.example.orderController;

import ch.qos.logback.classic.Logger;
import org.example.dto.OrderRequestDto;
import org.example.dto.OrderResponseDto;
import org.example.entity.Order;
import org.example.service.OrderService;
import org.example.util.ConvertToOrderDto;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    Logger logger = (Logger) LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private ConvertToOrderDto convertToOrderDto;

    @GetMapping("")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders(){
        List<Order> orders = orderService.allOrders();
        List<OrderResponseDto> orderListDto = orders.stream().map(x-> convertToOrderDto.convertToResponseDto(x)).collect(Collectors.toList()).reversed();

        return new ResponseEntity<>(orderListDto, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto){
        try{
            Order order = orderService.createOrder(orderRequestDto);
            OrderResponseDto responseDto = convertToOrderDto.convertToResponseDto(order);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            logger.error(e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
