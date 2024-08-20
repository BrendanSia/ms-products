package com.demoproject.brendansia.web;

import com.demoproject.brendansia.dto.ProductDTO;
import com.demoproject.brendansia.dto.SaveRequestDTO;
import com.demoproject.brendansia.entity.Product;
import com.demoproject.brendansia.repository.CsvService;
import com.demoproject.brendansia.service.CsvServiceImpl;
import com.demoproject.brendansia.service.ProductService;
import com.demoproject.brendansia.utils.CsvUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "@environment['MFE_ADDRESS']")
public class Controller {

    @Autowired
    private final ProductService productService;
    @Autowired
    private final CsvService csvService;

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

    @PostMapping("/async/upload")
    public ResponseEntity <String> csvAsyncProcessing(@RequestParam("file") MultipartFile[] files) {
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (MultipartFile file : files) {
            futures.add(csvService.saveAsync(file));
        }

        // Wait for all files to be processed
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        csvService.processAndSave();

        return ResponseEntity.ok("Files uploaded successfully");
    }

    @PostMapping("/virtual-thread/upload")
    public ResponseEntity<String> csvVirtualThreadProcessing(@RequestParam("file") MultipartFile[] files) {
        for (MultipartFile file : files) {
            csvService.saveVirtualThread(file);
        }

        csvService.processAndSave();

        return ResponseEntity.ok("Files uploaded successfully");
    }
}
