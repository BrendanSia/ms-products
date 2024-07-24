package com.demoproject.brendansia.repository;

import com.demoproject.brendansia.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query(value="SELECT * FROM t_product p WHERE p.code = :code", nativeQuery = true)
    Product getByCode(@Param("code")String code);

    @Query(value = "SELECT MAX(id) FROM t_product p", nativeQuery = true)
    Integer findMaxId();
}
