package com.demoproject.brendansia.dto;

import com.demoproject.brendansia.entity.Products;

public class ProductDTO {

    private Integer id;
    private String code;
    private String name;
    private String category;
    private String brand;
    private String type;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductDTO build(Products products) {
        ProductDTO item = new ProductDTO();
        item.id = products.getId();
        item.code = products.getCode();
        item.name = products.getName();
        item.category = products.getCategory();
        item.brand = products.getBrand();
        item.type = products.getType();
        item.description = products.getDescription();
        return item;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int idx = -1;
        if (id != null) {
            sb.append(++idx > 0 ? ", " : "").append("id=").append(id);
        }
        if (code != null) {
            sb.append(++idx > 0 ? ", " : "").append("code=").append(code);
        }
        if (name != null) {
            sb.append(++idx > 0 ? ", " : "").append("name=").append(name);
        }
        if (category != null) {
            sb.append(++idx > 0 ? ", " : "").append("category=").append(category);
        }
        if (brand != null) {
            sb.append(++idx > 0 ? ", " : "").append("brand=").append(brand);
        }
        if (type != null) {
            sb.append(++idx > 0 ? ", " : "").append("type=").append(type);
        }
        if (description != null) {
            sb.append(++idx > 0 ? ", " : "").append("description=").append(description);
        }
        return sb.toString();
    }

}

