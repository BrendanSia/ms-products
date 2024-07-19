package com.demoproject.brendansia.service;

import com.demoproject.brendansia.dto.ProductDTO;
import com.demoproject.brendansia.dto.SaveRequestDTO;
import com.demoproject.brendansia.entity.Products;
import com.demoproject.brendansia.exceptions.BaseException;
import com.demoproject.brendansia.repository.ProductsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DemoServiceTest {

    @InjectMocks
    private DemoService demoService;
    @Mock
    private ProductsRepository productsRepository;
    ObjectMapper objectMapper;
    Products product;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        demoService = new DemoService(objectMapper, productsRepository);
        product = new Products();
        product.setId(1);
        product.setCode("123");
        product.setName("ABC");
        product.setCategory("A");
        product.setBrand("B");
        product.setDescription("Description");
    }

    @Test
    void givenCode_whenRetrieveDetails_returnSuccess() throws Exception {
       when(productsRepository.getByCode("123")).thenReturn(product);

       ProductDTO result = demoService.retrieveDetails("123");

       assertEquals(product.getId(), result.getId());
       assertEquals(product.getCode(), result.getCode());
       assertEquals(product.getName(), result.getName());
       assertEquals(product.getCategory(), result.getCategory());
       assertEquals(product.getBrand(), result.getBrand());
       assertEquals(product.getDescription(), result.getDescription());
    }

    @Test
    void givenCode_whenRetrieveDetails_returnFailure() throws Exception {
        when(productsRepository.getByCode("123")).thenReturn(null);

        try {
            demoService.retrieveDetails("123");
        } catch (BaseException e) {
            assertEquals("Product with code 123 not found", e.getMessage());
        }

    }

    @Test
    void givenRequest_saveProduct_returnSuccess() {
        SaveRequestDTO requestDTO = new SaveRequestDTO();
        requestDTO.setCode("123");
        requestDTO.setName("Test Product");

        when(productsRepository.getByCode("123")).thenReturn(null);
        when(productsRepository.findMaxId()).thenReturn(0);

        boolean saved = demoService.saveDetail(requestDTO);
        assertTrue(saved);

        verify(productsRepository, times(1)).save(any());
    }

    @Test
    void givenRequest_saveProduct_returnFailure() throws Exception {
        String code = "XYZ123";
        SaveRequestDTO requestDTO = new SaveRequestDTO();
        requestDTO.setCode(code);

        Products existingProduct = new Products();
        existingProduct.setCode(code);

        Mockito.when(productsRepository.getByCode(code)).thenReturn(existingProduct);


        try {
            demoService.saveDetail(requestDTO);
        } catch (BaseException e) {
            assertEquals("Product already exists", e.getMessage());
        }
    }

    @Test
    void givenRequest_updateProduct_returnSuccess() {
        String code = "123";
        SaveRequestDTO requestDTO = new SaveRequestDTO();
        requestDTO.setCode(code);
        requestDTO.setName("Updated Product");

        Products existingProduct = new Products();
        existingProduct.setCode(code);

        when(productsRepository.getByCode(code)).thenReturn(existingProduct);

        String result = demoService.updateProduct(requestDTO, code);
        assertEquals("Record updated successfully", result);

        verify(productsRepository, times(1)).saveAndFlush(existingProduct);
    }

    @Test
    void givenRequest_updateProduct_returnFailure() {
        String code = "123";

        when(productsRepository.getByCode(code)).thenReturn(null);

        try {
            demoService.updateProduct(new SaveRequestDTO(), code);
        } catch (BaseException e) {
            assertEquals("Product does not exist", e.getMessage());
        }
    }
}
