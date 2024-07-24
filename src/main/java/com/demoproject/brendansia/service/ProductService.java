package com.demoproject.brendansia.service;

import com.demoproject.brendansia.dto.ProductDTO;
import com.demoproject.brendansia.dto.SaveRequestDTO;
import com.demoproject.brendansia.entity.Product;
import com.demoproject.brendansia.exceptions.ProductException;
import com.demoproject.brendansia.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import static com.demoproject.brendansia.constant.Error.*;

import java.util.Objects;

@Service
@Builder
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDTO retrieveDetails(String code) throws ProductException {
        Product product = productRepository.getByCode(code);
        if (Objects.isNull(product)) {
            throw new ProductException(PRODUCT_NOT_FOUND);
        }
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .code(product.getCode())
                .category(product.getCategory())
                .brand(product.getBrand())
                .type(product.getType())
                .description(product.getDescription())
                .build();
    }

    public boolean saveDetail(SaveRequestDTO requestDTO) throws ProductException {
        Product existingProduct = productRepository.getByCode(requestDTO.getCode());
        if (!Objects.isNull(existingProduct)) {
            throw new ProductException(PRODUCT_ALREADY_EXISTS);
        }
        Integer latestId = productRepository.findMaxId();
        Product product = new Product();
        product.setId(latestId + 1);
        product.setCode(requestDTO.getCode());
        product.setName(requestDTO.getName());
        product.setCategory(requestDTO.getCategory());
        product.setBrand(requestDTO.getBrand());
        product.setType(requestDTO.getType());
        product.setDescription(requestDTO.getDescription());
        productRepository.save(product);
        return true;
    }

    public String updateProduct(SaveRequestDTO requestDTO, String code){
        Product existingProduct = productRepository.getByCode(code);
        if (Objects.isNull(existingProduct)) {
            throw new ProductException(PRODUCT_NOT_FOUND);
        }
        existingProduct.setId(existingProduct.getId());
        existingProduct.setCode(requestDTO.getCode());
        existingProduct.setName(requestDTO.getName());
        existingProduct.setCategory(requestDTO.getCategory());
        existingProduct.setBrand(requestDTO.getBrand());
        existingProduct.setType(requestDTO.getType());
        existingProduct.setDescription(requestDTO.getDescription());

        productRepository.saveAndFlush(existingProduct);
        return "Record updated successfully";
    }

    public String deleteProduct (String code) {
        Product existingProduct = productRepository.getByCode(code);
        if (Objects.isNull(existingProduct)) {
            throw new ProductException(PRODUCT_NOT_FOUND);
        }
        productRepository.delete(existingProduct);
        return "Product deleted successfully";
    }

    public Page<Product> getAllProducts(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size));
    }
}
