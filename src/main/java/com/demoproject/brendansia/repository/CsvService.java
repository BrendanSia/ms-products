package com.demoproject.brendansia.repository;

import com.demoproject.brendansia.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CsvService {

    void save(MultipartFile file);
    CompletableFuture<Void> saveAsync(MultipartFile file);
    void saveVirtualThread(MultipartFile file);
    void processAndSave();
    List<Product> findAll();
}
