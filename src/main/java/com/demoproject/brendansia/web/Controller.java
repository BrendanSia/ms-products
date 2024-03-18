package com.demoproject.brendansia.web;

import com.demoproject.brendansia.dto.ProductDTO;
import com.demoproject.brendansia.dto.SaveRequestDTO;
import com.demoproject.brendansia.entity.Products;
import com.demoproject.brendansia.service.DemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class Controller {

    private final DemoService demoService;

    @GetMapping(value = "/detail/{code}")
    public ProductDTO getProductDetail(
            @PathVariable String code
    ){
        try{
            return demoService.retrieveDetailsGet(code);
        }catch(Exception e){
            return new ProductDTO();
        }
    }

    @PostMapping()
    public ResponseEntity<String> createProduct(
            @RequestBody SaveRequestDTO requestDTO
    ){
        try{
            boolean success = demoService.saveDetail(requestDTO);
            if (success) {
                return new ResponseEntity<>("Product saved successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Product already exists", HttpStatus.OK);
            }
        }catch(Exception e){
            throw new RuntimeException();
        }
    }

    @PostMapping(value="/process/{code}")
    public ResponseEntity<String> processProduct(
            @RequestBody SaveRequestDTO requestDTO,
            @PathVariable String code
    ){
        try{
            String response = demoService.processProduct(requestDTO, code);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e){
            throw new RuntimeException();
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
            throw new RuntimeException();
        }
    }

    @GetMapping("/list")
    public Page<Products> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return demoService.getAllProducts(page, size);
    }
}
