package com.demoproject.brendansia.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "t_product")
public class Product {
    @Id
    private Integer id;
    @Column(unique = true)
    private String code;
    private String name;
    private String category;
    private String brand;
    private String type;
    private String description;

}
