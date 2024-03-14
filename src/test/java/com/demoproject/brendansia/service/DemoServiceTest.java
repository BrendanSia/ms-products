package com.demoproject.brendansia.service;

import com.demoproject.brendansia.dto.ProductDTO;
import com.demoproject.brendansia.dto.SaveRequestDTO;
import com.demoproject.brendansia.entity.Products;
import com.demoproject.brendansia.repository.ProductsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class DemoServiceTest {

    @Mock
    private ProductsRepository productsRepository;

    @Mock
    private ObjectMapper objectMapper;

    private DemoService demoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        demoService = new DemoService(objectMapper, productsRepository);
    }

    @Test
    void testSaveDetail() {
        SaveRequestDTO requestDTO = new SaveRequestDTO();
        requestDTO.setCode("123");
        requestDTO.setName("Test Product");

        Products existingProduct = new Products();
        existingProduct.setCode("123");

        when(productsRepository.getByCode("123")).thenReturn(null);
        when(productsRepository.findMaxId()).thenReturn(0);

        boolean saved = demoService.saveDetail(requestDTO);
        assertTrue(saved);

        verify(productsRepository, times(1)).save(any());
    }

    @Test
    void testRetrieveDetailsGet() {
        String code = "123";
        Products product = new Products();
        product.setCode(code);
        product.setName("Test Product");

        when(productsRepository.getByCode(code)).thenReturn(product);

        ProductDTO productDTO = demoService.retrieveDetailsGet(code);

        assertEquals(productDTO.getCode(), code);
        assertEquals("Test Product", productDTO.getName());
    }

    @Test
    void testProcessProduct() {
        String code = "123";
        SaveRequestDTO requestDTO = new SaveRequestDTO();
        requestDTO.setCode(code);
        requestDTO.setName("Updated Product");

        Products existingProduct = new Products();
        existingProduct.setCode(code);

        when(productsRepository.getByCode(code)).thenReturn(existingProduct);

        String result = demoService.processProduct(requestDTO, code);
        assertEquals("Record updated successfully", result);

        verify(productsRepository, times(1)).saveAndFlush(existingProduct);
    }

    @Test
    void testDeleteProduct() {
        String code = "123";
        Products existingProduct = new Products();
        existingProduct.setCode(code);

        when(productsRepository.getByCode(code)).thenReturn(existingProduct);

        String result = demoService.deleteProduct(code);
        assertEquals("Product deleted successfully", result);

        verify(productsRepository, times(1)).delete(existingProduct);
    }

    @Test
    void testGetAllProducts() {
        int page = 0;
        int size = 10;
        Page<Products> productsPage = mock(Page.class);

        when(productsRepository.findAll(PageRequest.of(page, size))).thenReturn(productsPage);

        Page<Products> result = demoService.getAllProducts(page, size);
        assertEquals(productsPage, result);
    }
}
