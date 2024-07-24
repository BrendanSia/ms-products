package com.demoproject.brendansia.web;

import com.demoproject.brendansia.dto.ProductDTO;
import com.demoproject.brendansia.dto.SaveRequestDTO;
import com.demoproject.brendansia.entity.Product;
import com.demoproject.brendansia.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "@environment['MFE_ADDRESS']")
public class Controller {

    @Autowired
    private final ProductService productService;

    @GetMapping(value = "/detail/{code}")
    public ResponseEntity<Object> getProductDetail(
            @PathVariable String code
    ) {
        ProductDTO productDTO = productService.retrieveDetails(code);
        return ResponseEntity.ok(productDTO);
    }

    @PostMapping(value="/create")
    public ResponseEntity<String> createProduct(
            @Valid @RequestBody SaveRequestDTO requestDTO
    ){
        productService.saveDetail(requestDTO);
        return ResponseEntity.ok("Product saved successfully.");
    }

    @PostMapping(value="/update/{code}")
    public ResponseEntity<String> processProduct(
            @Valid @RequestBody SaveRequestDTO requestDTO,
            @PathVariable String code
    ){
        String response = productService.updateProduct(requestDTO, code);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value="/delete/{code}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable String code
    ){
        String response = productService.deleteProduct(code);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list")
    public Page<Product> getProducts(
            @RequestParam(required = true) int page,
            @RequestParam(required = true) int size
    ) {
        return productService.getAllProducts(page, size);
    }
}
