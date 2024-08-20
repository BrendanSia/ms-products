package com.demoproject.brendansia.mapper;

import com.demoproject.brendansia.dto.ProductDTO;
import com.demoproject.brendansia.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public Product toProductEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setCode(productDTO.getCode());
        product.setName(product.getName());
        product.setCategory(productDTO.getCategory());
        product.setBrand(productDTO.getBrand());
        product.setType(productDTO.getType());
        product.setDescription(productDTO.getDescription());
        return product;
    }

    public List<Product> toProductEntityList(List<ProductDTO> productDTOs) {
        return productDTOs.stream()
                .map(this::toProductEntity)
                .collect(Collectors.toList());
    }
}
