package com.demoproject.brendansia.web;

import com.demoproject.brendansia.dto.ProductDTO;
import com.demoproject.brendansia.dto.SaveRequestDTO;
import com.demoproject.brendansia.entity.Products;
import com.demoproject.brendansia.exceptions.BaseException;
import com.demoproject.brendansia.service.DemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class Controller {

    @Autowired
    private final DemoService demoService;

    @GetMapping(value = "/detail/{code}")
    public ResponseEntity<Object> getProductDetail(
            @PathVariable String code
    ) {
        try {
            ProductDTO productDTO = demoService.retrieveDetails(code);
            return ResponseEntity.ok(productDTO);
        } catch (BaseException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
    }

    @PostMapping(value="/create")
    public ResponseEntity<String> createProduct(
            @RequestBody SaveRequestDTO requestDTO
    ){
        try {
            demoService.saveDetail(requestDTO);
            return ResponseEntity.ok("Product saved successfully.");
        } catch (BaseException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
    }

    @PostMapping(value="/update/{code}")
    public ResponseEntity<String> processProduct(
            @RequestBody SaveRequestDTO requestDTO,
            @PathVariable String code
    ){
        try{
            String response = demoService.updateProduct(requestDTO, code);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (BaseException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
    }

    @PostMapping(value="/delete/{code}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable String code
    ){
        try{
            String response = demoService.deleteProduct(code);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e){
            throw new IllegalArgumentException("Error deleting record");
        }
    }

    @GetMapping("/list")
    public Page<Products> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return demoService.getAllProducts(page, size);
    }
}
