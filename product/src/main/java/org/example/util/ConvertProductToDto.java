package org.example.util;

import lombok.Getter;
import lombok.Setter;
import org.example.dto.ProductResponseDto;
import org.example.entity.Product;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ConvertProductToDto {
    public ProductResponseDto convertProductToProductResponseDto (Product product){
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setProductName(product.getName());
        return productResponseDto;
    }
}
