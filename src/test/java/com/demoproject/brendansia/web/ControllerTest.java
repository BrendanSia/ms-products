package com.demoproject.brendansia.web;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.demoproject.brendansia.constant.Error;
import com.demoproject.brendansia.dto.ProductDTO;

import com.demoproject.brendansia.dto.SaveRequestDTO;
import com.demoproject.brendansia.exceptions.ProductException;
import com.demoproject.brendansia.service.ProductService;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


@ExtendWith(MockitoExtension.class)
class ControllerTest {
    @InjectMocks
    private Controller controller;
    @Mock
    private ProductService productService;


    ObjectMapper objectMapper;
    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        mockMvc = standaloneSetup(controller)
                .build();
    }
    @Test
    @SneakyThrows
    void getProductDetail_returnResult() {
        when(productService.retrieveDetails("123")).thenReturn(new ProductDTO());

        MvcResult mvcResult = mockMvc.perform(get("/api/products/detail/123"))
                .andExpect(status().isOk()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        ProductDTO actualProductDTO = objectMapper.readValue(response, ProductDTO.class);
        Assertions.assertEquals(new ProductDTO(), actualProductDTO);
    }

    @Test
    @SneakyThrows
    void getProductDetail_returnFailure() {
        String code = "123";
        when(productService.retrieveDetails(code)).thenThrow(ProductException.class);

        assertThrows(ProductException.class, () -> controller.getProductDetail(code));
    }

    @Test
    @SneakyThrows
    void createProduct_returnSuccess(){
        String requestBody = "{ \"id\": 12, \"code\": \"XYZ123\", \"name\": \"Product Name\", \"description\": \"Product Description\" }";
        SaveRequestDTO requestDTO = new ObjectMapper().readValue(requestBody, SaveRequestDTO.class);
        productService.saveDetail(requestDTO);

        MvcResult mvcResult = mockMvc.perform(post("/api/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                        .andExpect(status().isOk())
                        .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals("Product saved successfully.", responseBody.trim());
    }

    @Test
    @SneakyThrows
    void createProduct_fail_existingProduct() {
        String requestBody = "{ \"code\": \"XYZ123\", \"name\": \"Product Name\", \"description\": \"Product Description\" }";
        SaveRequestDTO requestDTO = new ObjectMapper().readValue(requestBody, SaveRequestDTO.class);

        when(productService.saveDetail(requestDTO)).thenThrow(new ProductException(Error.PRODUCT_ALREADY_EXISTS));

        assertThrows(ProductException.class, () -> controller.createProduct(requestDTO));
    }

    @Test
    @SneakyThrows
    void updateProduct_returnSuccess(){
        String requestBody = "{ \"id\": 16, \"code\": \" 123 \", \"name\": \"Updated Product Name\", \"description\": \"Updated Description\" }";
        SaveRequestDTO requestDTO = new ObjectMapper().readValue(requestBody, SaveRequestDTO.class);

        Mockito.when(productService.updateProduct(requestDTO, "123")).thenReturn("Record updated successfully");

        MvcResult mvcResult = mockMvc.perform(post("/api/products/update/{code}", "123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                        .andExpect(status().isOk())
                        .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals("Record updated successfully", responseBody.trim());
    }

    @Test
    @SneakyThrows
    void updateProduct_returnFailure(){
        String requestBody = "{ \"code\": \" 123 \", \"name\": \"Updated Product Name\", \"description\": \"Updated Description\" }";
        SaveRequestDTO requestDTO = new ObjectMapper().readValue(requestBody, SaveRequestDTO.class);

        when(productService.updateProduct(requestDTO, "123")).thenThrow(new ProductException(Error.PRODUCT_NOT_FOUND));

        assertThrows(ProductException.class, () -> controller.processProduct(requestDTO, "123"));
    }
}
