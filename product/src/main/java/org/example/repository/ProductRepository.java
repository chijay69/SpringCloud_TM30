package org.example.repository;


import jakarta.transaction.Transactional;
import org.example.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProductRepository {

    private final Map<Long, Product> productStore = new HashMap<>();


    public Product save(Product product) {
        productStore.put(product.getId(), product);
        return product;
    }

    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(productStore.get(id));
    }

    public List<Product> findAll() {
        return new ArrayList<>(productStore.values());
    }

    public void delete(Product product) {
        productStore.remove(product.getId());
    }
}