package com.demoproject.brendansia.repository;

import com.demoproject.brendansia.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.Optional;
import java.util.UUID;

public interface ProductsRepository extends JpaRepository<Products, UUID> {
    @Query(value="SELECT p FROM Products p WHERE p.code = :code")
    Products getByCode(@Param("code")String code);

    @Query("SELECT MAX(p.id) FROM Products p")
    Integer findMaxId();
}
