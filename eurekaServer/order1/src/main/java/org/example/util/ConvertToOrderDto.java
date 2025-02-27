package org.example.util;


import lombok.Getter;
import lombok.Setter;
import org.example.dto.OrderResponseDto;
import org.example.entity.Order;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ConvertToOrderDto {

    public OrderResponseDto convertToResponseDto(Order order){
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setOrderId(order.getId());
        orderResponseDto.setQuantity(order.getQuantity());
        return orderResponseDto;
    }
}
