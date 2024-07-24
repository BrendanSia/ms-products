package com.demoproject.brendansia.entity;

import jakarta.persistence.*;
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
    @Column
    private String name;
    @Column
    private String category;
    @Column
    private String brand;
    @Column
    private String type;
    @Column
    private String description;

}
