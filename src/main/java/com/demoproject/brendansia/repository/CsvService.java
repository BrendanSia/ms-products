package com.demoproject.brendansia.repository;

import com.demoproject.brendansia.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CsvService {
    CompletableFuture<Void> saveAsync(MultipartFile file);
    void saveVirtualThread(MultipartFile file);
    void saveFixedThreadPool(MultipartFile file);
    void saveSingleThread(MultipartFile file);
    void processAndSaveVirtualThread();
    void processAndSaveFixedThreadPool();
    void processAndSaveSingleThread();

    List<Product> findAll();
}
