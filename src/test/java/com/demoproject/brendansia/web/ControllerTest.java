package com.demoproject.brendansia.web;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.demoproject.brendansia.dto.ProductDTO;
import com.demoproject.brendansia.dto.SaveRequestDTO;
import com.demoproject.brendansia.entity.Products;
import com.demoproject.brendansia.service.DemoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class ControllerTest {

    @Mock
    private DemoService demoService;

    @InjectMocks
    private Controller controller;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testGetProductDetail() {
        ProductDTO productDTO = new ProductDTO(); // You can create a sample DTO for testing
        when(demoService.retrieveDetailsGet("sampleCode")).thenReturn(productDTO);

        ProductDTO result = controller.getProductDetail("sampleCode");

        assertNotNull(result);
    }

    @Test
    void testCreateProduct() {
        SaveRequestDTO requestDTO = new SaveRequestDTO(); // You can create a sample request DTO for testing
        when(demoService.saveDetail(requestDTO)).thenReturn(true); // Assuming success for testing

        ResponseEntity<String> responseEntity = controller.createProduct(requestDTO);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Product saved successfully", responseEntity.getBody());
    }

    @Test
    void testProcessProduct() {
        SaveRequestDTO requestDTO = new SaveRequestDTO(); // You can create a sample request DTO for testing
        String code = "sampleCode";
        when(demoService.processProduct(requestDTO, code)).thenReturn("Processed successfully"); // Assuming success for testing

        ResponseEntity<String> responseEntity = controller.processProduct(requestDTO, code);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Processed successfully", responseEntity.getBody());
    }

    @Test
    void testDeleteProduct() {
        String code = "sampleCode";
        when(demoService.deleteProduct(code)).thenReturn("Deleted successfully"); // Assuming success for testing

        ResponseEntity<String> responseEntity = controller.deleteProduct(code);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Deleted successfully", responseEntity.getBody());
    }

    @Test
    void testGetProducts() {
        Page<Products> productsPage = mock(Page.class);

        when(demoService.getAllProducts(0, 10)).thenReturn(productsPage);

        Page<Products> result = controller.getProducts(0, 10);

        assertNotNull(result);
    }
}
