package com.demoproject.brendansia.service;

import com.demoproject.brendansia.dto.ProductDTO;
import com.demoproject.brendansia.dto.SaveRequestDTO;
import com.demoproject.brendansia.entity.Product;
import com.demoproject.brendansia.exceptions.ProductException;
import com.demoproject.brendansia.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    Product product;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository);
        product = new Product();
        product.setId(1);
        product.setCode("123");
        product.setName("ABC");
        product.setCategory("A");
        product.setBrand("B");
        product.setDescription("Description");
    }

    @Test
    void givenCode_whenRetrieveDetails_returnSuccess() throws Exception {
       when(productRepository.findByCode("123")).thenReturn(product);

       ProductDTO result = productService.retrieveDetails("123");

       assertEquals(product.getId(), result.getId());
       assertEquals(product.getCode(), result.getCode());
       assertEquals(product.getName(), result.getName());
       assertEquals(product.getCategory(), result.getCategory());
       assertEquals(product.getBrand(), result.getBrand());
       assertEquals(product.getDescription(), result.getDescription());
    }

    @Test
    void givenCode_whenRetrieveDetails_returnFailure() throws Exception {
        when(productRepository.findByCode("123")).thenReturn(null);

        try {
            productService.retrieveDetails("123");
        } catch (ProductException e) {
            assertEquals("Product does not exist", e.getMessage());
        }

    }

    @Test
    void givenRequest_saveProduct_returnSuccess() {
        SaveRequestDTO requestDTO = new SaveRequestDTO();
        requestDTO.setCode("123");
        requestDTO.setName("Test Product");

        when(productRepository.findByCode("123")).thenReturn(null);
        when(productRepository.findTopByOrderByIdDesc()).thenReturn(product);

        boolean saved = productService.saveDetail(requestDTO);
        assertTrue(saved);

        verify(productRepository, times(1)).save(any());
    }

    @Test
    void givenRequest_saveProduct_returnFailure() throws Exception {
        String code = "XYZ123";
        SaveRequestDTO requestDTO = new SaveRequestDTO();
        requestDTO.setCode(code);

        Product existingProduct = new Product();
        existingProduct.setCode(code);

        Mockito.when(productRepository.findByCode(code)).thenReturn(existingProduct);


        try {
            productService.saveDetail(requestDTO);
        } catch (ProductException e) {
            assertEquals("Product already exists", e.getMessage());
        }
    }

    @Test
    void givenRequest_updateProduct_returnSuccess() {
        String code = "123";
        SaveRequestDTO requestDTO = new SaveRequestDTO();
        requestDTO.setCode(code);
        requestDTO.setName("Updated Product");

        Product existingProduct = new Product();
        existingProduct.setCode(code);

        when(productRepository.findByCode(code)).thenReturn(existingProduct);

        String result = productService.updateProduct(requestDTO, code);
        assertEquals("Record updated successfully", result);

        verify(productRepository, times(1)).saveAndFlush(existingProduct);
    }

    @Test
    void givenRequest_updateProduct_returnFailure() {
        String code = "123";

        when(productRepository.findByCode(code)).thenReturn(null);

        try {
            productService.updateProduct(new SaveRequestDTO(), code);
        } catch (ProductException e) {
            assertEquals("Product does not exist", e.getMessage());
        }
    }
}
