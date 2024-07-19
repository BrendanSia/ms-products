package com.demoproject.brendansia.service;

import com.demoproject.brendansia.dto.ProductDTO;
import com.demoproject.brendansia.dto.SaveRequestDTO;
import com.demoproject.brendansia.entity.Products;
import com.demoproject.brendansia.exceptions.BaseException;
import com.demoproject.brendansia.repository.ProductsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class DemoService {

    private ObjectMapper objectMapper;
    private ProductsRepository productsRepository;

    public ProductDTO retrieveDetails(String code) {
        Products product = productsRepository.getByCode(code);
        if (product != null) {
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

        throw new BaseException("Product with code " + code + " not found");
    }

    public boolean saveDetail(SaveRequestDTO requestDTO){
        Products existingProduct = productsRepository.getByCode(requestDTO.getCode());
        if (Objects.isNull(existingProduct)) {
            Integer latestId = productsRepository.findMaxId();
            Products product = new Products();
            product.setId(latestId + 1);
            product.setCode(requestDTO.getCode());
            product.setName(requestDTO.getName());
            product.setCategory(requestDTO.getCategory());
            product.setBrand(requestDTO.getBrand());
            product.setType(requestDTO.getType());
            product.setDescription(requestDTO.getDescription());
            productsRepository.save(product);
            return true;
        }

        throw new BaseException("Product already exists");
    }

    public String updateProduct(SaveRequestDTO requestDTO, String code){
        Products existingProduct = productsRepository.getByCode(code);
        if (!Objects.isNull(existingProduct)) {
            existingProduct.setId(existingProduct.getId());
            existingProduct.setCode(requestDTO.getCode());
            existingProduct.setName(requestDTO.getName());
            existingProduct.setCategory(requestDTO.getCategory());
            existingProduct.setBrand(requestDTO.getBrand());
            existingProduct.setType(requestDTO.getType());
            existingProduct.setDescription(requestDTO.getDescription());

            productsRepository.saveAndFlush(existingProduct);
            return "Record updated successfully";
        }

        throw new BaseException("Product does not exist");
    }

    public String deleteProduct (String code) {
        Products existingProduct = productsRepository.getByCode(code);
        if (Objects.isNull(existingProduct)) {
            return "Product does not exist";
        }
        productsRepository.delete(existingProduct);
        return "Product deleted successfully";
    }

    public Page<Products> getAllProducts(int page, int size) {
        return productsRepository.findAll(PageRequest.of(page, size));
    }
}
