package org.example.service;

import org.example.dto.ProductRequestDto;
import org.example.entity.Product;
import org.example.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplementationTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImplementation productService;

    private ProductRequestDto productRequestDto;
    private Product product;

    @BeforeEach
    void setUp() {
        productRequestDto = new ProductRequestDto();
        productRequestDto.setProductId(1L);
        productRequestDto.setProductName("Test Product");
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
    }

    @Test
    void findById_productFound_returnsProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.findById(1);

        assertEquals(product, result);
    }

    @Test
    void findById_productNotFound_throwsRuntimeException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.findById(1));
    }

    @Test
    void findAllProducts_productsFound_returnsProductList() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(product));

        List<Product> result = productService.findAllProducts();

        assertEquals(1, result.size());
        assertEquals(product, result.get(0));
    }

    @Test
    void findAllProducts_productsNotFound_returnsEmptyList() {
        when(productRepository.findAll()).thenReturn(List.of());

        List<Product> result = productService.findAllProducts();

        assertTrue(result.isEmpty());
    }

    @Test
    void createProduct_validRequest_returnsCreatedProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.createProduct(productRequestDto);

        assertEquals(product, result);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void createProduct_exceptionThrown_throwsRuntimeException() {
        when(productRepository.save(any(Product.class))).thenThrow(new RuntimeException("Test Exception"));

        assertThrows(RuntimeException.class, () -> productService.createProduct(productRequestDto));
    }

    @Test
    void updateProduct_productFound_returnsUpdatedProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.updateProduct(productRequestDto);

        assertEquals(product, result);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void updateProduct_productNotFound_returnsNull() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Product result = productService.updateProduct(productRequestDto);

        assertNull(result);
    }

    @Test
    void updateProduct_exceptionThrown_returnsNull() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenThrow(new RuntimeException("Test Exception"));

        Product result = productService.updateProduct(productRequestDto);

        assertNull(result);
    }

    @Test
    void deleteProduct_productFound_returnsSuccessMessage() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        String result = productService.deleteProduct(1);

        assertEquals("delete successful", result);
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void deleteProduct_productNotFound_returnsFailureMessage() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        String result = productService.deleteProduct(1);

        assertEquals("delete failure", result);
        verify(productRepository, never()).delete(any(Product.class));
    }
}