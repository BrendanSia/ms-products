package com.demoproject.brendansia.service;

import com.demoproject.brendansia.dto.ProductDTO;
import com.demoproject.brendansia.dto.SaveRequestDTO;
import com.demoproject.brendansia.entity.Products;
import com.demoproject.brendansia.repository.ProductsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class DemoService {

    private final ObjectMapper objectMapper;
    private final ProductsRepository productsRepository;

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
        } else {
            return false;
        }
    }

    public ProductDTO retrieveDetailsGet(String code) {
        Products product = productsRepository.getByCode(code);
        if(!Objects.isNull(product)){
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
        return new ProductDTO();
    }

    public String processProduct(SaveRequestDTO requestDTO, String code){
        Products existingProduct = productsRepository.getByCode(code);
        if (Objects.isNull(existingProduct)) {
            return "Product does not exist";
        } else {
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

    public String getResponse(String path) throws IOException {
        return new String(Files.readAllBytes(Path.of(path)));
    }
}
