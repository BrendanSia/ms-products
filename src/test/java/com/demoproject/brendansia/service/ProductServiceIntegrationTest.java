package com.demoproject.brendansia.service;

import com.demoproject.brendansia.dto.ProductDTO;
import com.demoproject.brendansia.dto.SaveRequestDTO;
import com.demoproject.brendansia.entity.Product;
import com.demoproject.brendansia.exceptions.ProductException;
import com.demoproject.brendansia.repository.ProductRepository;
import com.demoproject.brendansia.service.ProductService;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
    public void givenProduct_whenSave_thenReturnSuccess() {
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
    public void givenInvalidProduct_whenSave_thenFail() {
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
}
