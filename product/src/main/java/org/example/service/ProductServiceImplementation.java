package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.ProductRequestDto;
import org.example.entity.Product;
import org.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductServiceImplementation implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product findById(Integer productId) {
        try {
            return productRepository.findById(Long.valueOf(productId)).orElseThrow();
        } catch (Exception e) {
            log.error("An error occurred attempting to find product with id: {}, {}", productId, e.getMessage());
            throw new RuntimeException("Error retrieving product.");
        }
    }

    @Override
    public List<Product> findAllProducts() {
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage());
            return List.of();
        }
    }

    @Override
    public Product createProduct(ProductRequestDto productRequestDto) {
        try {
            Product prod = new Product();
            prod.setId(productRequestDto.getProductId());
            prod.setName(productRequestDto.getProductName());
            return productRepository.save(prod);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product updateProduct(ProductRequestDto productRequestDto) {
        try {
            Optional<Product> optionalProduct = productRepository.findById(productRequestDto.getProductId());
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                product.setName(productRequestDto.getProductName());
                return productRepository.save(product);
            } else {
                return null;
            }
        } catch (Exception exception) {
            log.error("Error updating. msg: {}", exception.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public String deleteProduct(Integer productId) {
        Optional<Product> productOptional = productRepository.findById(Long.valueOf(productId));
        if (productOptional.isEmpty()) {
            log.error("Product with id: {} does not exists", productId);
            return "delete failure";
        }
        Product product = productOptional.get();
        productRepository.delete(product);
        return "delete successful";
    }
}