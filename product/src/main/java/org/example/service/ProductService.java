package org.example.service;


import org.example.dto.ProductRequestDto;
import org.example.entity.Product;

import java.util.List;

public interface ProductService {

    Product findById(Integer productId);

    List<Product> findAllProducts();

    Product createProduct(ProductRequestDto productRequestDto);

    Product updateProduct(ProductRequestDto productRequestDto);

    String deleteProduct(Integer productId);
}
