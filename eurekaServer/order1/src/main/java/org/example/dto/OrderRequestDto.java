package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto{
        Long orderId;
        Integer quantity;
        Long productId;
}
