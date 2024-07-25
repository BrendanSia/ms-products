package com.demoproject.brendansia.repository;

import com.demoproject.brendansia.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Product findByCode(String code);

    Product findTopByOrderByIdDesc();
}
