package com.demoproject.brendansia.web;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.demoproject.brendansia.dto.ProductDTO;

import com.demoproject.brendansia.dto.SaveRequestDTO;
import com.demoproject.brendansia.entity.Products;
import com.demoproject.brendansia.exceptions.BaseException;
import com.demoproject.brendansia.repository.ProductsRepository;
import com.demoproject.brendansia.service.DemoService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.RequestHeader;


@ExtendWith(MockitoExtension.class)
class ControllerTest {
    @InjectMocks
    private Controller controller;
    @Mock
    private DemoService demoService;
    @Mock
    private ProductsRepository productsRepository;

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
        when(demoService.retrieveDetails("123")).thenReturn(new ProductDTO());

        MvcResult mvcResult = mockMvc.perform(get("/api/products/detail/123"))
                .andExpect(status().isOk()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        ProductDTO actualProductDTO = objectMapper.readValue(response, ProductDTO.class);
        Assertions.assertEquals(new ProductDTO(), actualProductDTO);
    }

    @Test
    @SneakyThrows
    void getProductDetail_returnFailure() {
        when(demoService.retrieveDetails("123")).thenThrow(new BaseException("Product with code 123 not found"));

        MvcResult mvcResult = mockMvc.perform(get("/api/products/detail/123"))
                .andExpect(status().isOk()).andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals("Product with code 123 not found", responseJson.trim());
    }

    @Test
    @SneakyThrows
    void createProduct_returnSuccess(){
        String requestBody = "{ \"code\": \"XYZ123\", \"name\": \"Product Name\", \"description\": \"Product Description\" }";
        SaveRequestDTO requestDTO = new ObjectMapper().readValue(requestBody, SaveRequestDTO.class);
        demoService.saveDetail(requestDTO);

        MvcResult mvcResult = mockMvc.perform(post("/api/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();

        // Assert response body (optional)
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals("Product saved successfully.", responseBody.trim());
    }

    @Test
    @SneakyThrows
    void createProduct_fail_existingProduct() {
        String requestBody = "{ \"code\": \"XYZ123\", \"name\": \"Product Name\", \"description\": \"Product Description\" }";
        SaveRequestDTO requestDTO = new ObjectMapper().readValue(requestBody, SaveRequestDTO.class);

        when(demoService.saveDetail(requestDTO)).thenThrow(new BaseException("Product already exists"));

        MvcResult mvcResult = mockMvc.perform(post("/api/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals("Product already exists", responseBody.trim());
    }

    @Test
    @SneakyThrows
    void updateProduct_returnSuccess(){
        String requestBody = "{ \"code\": \" 123 \", \"name\": \"Updated Product Name\", \"description\": \"Updated Description\" }";
        SaveRequestDTO requestDTO = new ObjectMapper().readValue(requestBody, SaveRequestDTO.class);

        Mockito.when(demoService.updateProduct(requestDTO, "123")).thenReturn("Record updated successfully");

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

        Mockito.when(demoService.updateProduct(requestDTO, "123")).thenThrow(new BaseException("Product does not exist"));

        MvcResult mvcResult = mockMvc.perform(post("/api/products/update/{code}", "123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals("Product does not exist", responseBody.trim());
    }
}
