package org.example.util;


import lombok.Getter;
import lombok.Setter;
import org.example.dto.ProductRequestDto;
import org.example.entity.Product;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class convertDtoToProduct {
    public Product convertToProduct(ProductRequestDto requestDto){
        Product prod = new Product();
        prod.setId(requestDto.getProductId());
        prod.setName(requestDto.getProductName());
        return prod;
    }
}
