package com.demoproject.brendansia.service;

import com.demoproject.brendansia.dto.ProductDTO;
import com.demoproject.brendansia.dto.SaveRequestDTO;
import com.demoproject.brendansia.entity.Product;
import com.demoproject.brendansia.exceptions.ProductException;
import com.demoproject.brendansia.repository.ProductRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class ProductServiceIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Test
    public void givenProduct_whenGetByCode_thenReturnSuccess() {
        ProductDTO productDTO = productService.retrieveDetails("P001");

        assertNotNull(productDTO);
        assertEquals("P001", productDTO.getCode());
    }

    @Test
    public void givenProduct_whenGetByCode_thenReturnFail() {
        try {
            productService.retrieveDetails("123");
        } catch (ProductException e) {
            assertEquals("Product does not exist", e.getMessage());
        }
    }

    @Test
    public void givenValidProduct_whenGetAll_thenReturnSuccess() {
        Page<Product> productList = productService.getAllProducts(0, 10);

        assertNotNull(productList);
        assertEquals(productList.getTotalElements(), 4);
    }

    @Test
    public void givenProduct_whenCreate_thenReturnSuccess() {
        SaveRequestDTO requestDTO = SaveRequestDTO.builder()
                .id(4)
                .code("P004")
                .name("ABC")
                .category("A")
                .brand("B")
                .description("Description")
                .build();

        productService.saveDetail(requestDTO);

        Product product = productRepository.findByCode("P004");

        assertNotNull(product);
        assertEquals("ABC", product.getName());
    }

    @Test
    public void givenInvalidProduct_whenCreate_thenFail() {
        SaveRequestDTO requestDTO = SaveRequestDTO.builder()
                .id(1)
                .code("123")
                .name("ABC")
                .category("A")
                .brand("B")
                .description("Description")
                .build();

        try {
            productService.saveDetail(requestDTO);
        } catch (ProductException e) {
            Assertions.assertEquals("Product already exists", e.getMessage());
        }
    }

    @Test
    public void givenValidProduct_whenUpdate_thenReturnSuccess() {
        SaveRequestDTO requestDTO = SaveRequestDTO.builder()
                .id(1)
                .code("P001")
                .name("ABCD")
                .category("AB")
                .brand("BC")
                .description("Description")
                .build();

        productService.updateProduct(requestDTO, requestDTO.getCode());

        Product product = productRepository.findByCode("P001");

        assertNotNull(product);
        assertEquals("ABCD", product.getName());
    }

    @Test
    public void givenInvalidProduct_whenUpdate_thenReturnFail() {
        SaveRequestDTO requestDTO = SaveRequestDTO.builder()
                .id(1)
                .code("123")
                .name("ABCD")
                .category("AB")
                .brand("BC")
                .description("Description")
                .build();

        try {
            productService.updateProduct(requestDTO, requestDTO.getCode());
        } catch (ProductException e) {
            Assertions.assertEquals("Product does not exist", e.getMessage());
        }
    }

    @Test
    public void givenValidProduct_whenDelete_thenReturnSuccess() {
        productService.deleteProduct("P003");

        try {
            productService.retrieveDetails("P003");
        } catch (ProductException e) {
            Assertions.assertEquals("Product does not exist", e.getMessage());
        }
    }
}
