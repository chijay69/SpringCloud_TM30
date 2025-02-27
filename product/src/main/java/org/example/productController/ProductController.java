package org.example.productController;

import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ProductRequestDto;
import org.example.dto.ProductResponseDto;
import org.example.entity.Product;
import org.example.service.ProductService;
import org.example.util.ConvertProductToDto;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    private final ConvertProductToDto productToDto;
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService, ConvertProductToDto productToDto) {
        this.productService = productService;
        this.productToDto = productToDto;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> productList = productService.findAllProducts().stream()
                .map(productToDto::convertProductToProductResponseDto)
                .collect(Collectors.toList());
        log.info("getAllProducts called.");
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProductWithId(@PathVariable("productId") int productId) {
        try {
            Product product = productService.findById(productId);
            ProductResponseDto productResponseDto = productToDto.convertProductToProductResponseDto(product);
            log.info("Gotten product with id: {}", productId);
            return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error getting product with id: {}", productId, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createNewProduct(@RequestBody ProductRequestDto requestDto) {
        try {
            Product product = productService.createProduct(requestDto);
            ProductResponseDto productResponseDto = productToDto.convertProductToProductResponseDto(product);
            return new ResponseEntity<>(productResponseDto, HttpStatus.CREATED);
        } catch (RuntimeException exception) {
            log.error("Error creating product: {}", exception.getLocalizedMessage(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> updateProductWithId(@PathVariable("productId") int productId, @RequestBody ProductRequestDto productRequestDto) {
        try {
            log.info("product id --------------------------: {}", productId);
            Product product = productService.updateProduct(productRequestDto);
            if (product == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            ProductResponseDto responseDto = productToDto.convertProductToProductResponseDto(product);
            log.info("Updated product with id: {}", productId);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (RuntimeException exception) {
            log.error("Error updating product with id: {}", productId, exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProductWithId(@PathVariable("productId") int productId) {
        String response = productService.deleteProduct(productId);
        log.info("Deleted product with id: {}", productId);
        if ("delete successful".equals(response)) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}