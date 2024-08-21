package com.demoproject.brendansia.service;

import com.demoproject.brendansia.entity.Product;
import com.demoproject.brendansia.repository.CsvService;
import com.demoproject.brendansia.repository.ProductRepository;
import com.demoproject.brendansia.utils.CsvUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

@Service
public class CsvServiceImpl implements CsvService {
    @Autowired
    ProductRepository productRepository;
    private final ConcurrentLinkedQueue<Product> productQueue = new ConcurrentLinkedQueue<>();

    @Async
    public CompletableFuture<Void> saveAsync(MultipartFile file) {
        return CompletableFuture.runAsync(() -> {
            try {
                List<Product> productList = CsvUtil.csvToList(file.getInputStream());
                productQueue.addAll(productList);
            } catch (IOException ex) {
                throw new RuntimeException("Data is not store successfully: " + ex.getMessage());  // to implement exception handling
            }
        });
    }

    public void saveVirtualThread(MultipartFile file) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(() -> {
                try {
                    List<Product> productList = CsvUtil.csvToList(file.getInputStream());
                    productQueue.addAll(productList);
                } catch (IOException ex) {
                    throw new RuntimeException("Data is not store successfully: " + ex.getMessage());  // to implement exception handling
                }
            });
        }
    }

    public void saveFixedThreadPool(MultipartFile file) {
        try (var executor = Executors.newFixedThreadPool(10)) {
            executor.submit(() -> {
                try {
                    List<Product> productList = CsvUtil.csvToList(file.getInputStream());
                    productQueue.addAll(productList);
                } catch (IOException ex) {
                    throw new RuntimeException("Data is not stored successfully: " + ex.getMessage()); // to implement exception handling
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Failed to process the file using fixed thread pool: " + e.getMessage(), e);
        }
    }

    public void saveSingleThread(MultipartFile file) {
        try (var executor = Executors.newSingleThreadExecutor()) {
            executor.submit(() -> {
                try {
                    List<Product> productList = CsvUtil.csvToList(file.getInputStream());
                    productQueue.addAll(productList);
                } catch (IOException ex) {
                    throw new RuntimeException("Data is not stored successfully: " + ex.getMessage()); // to implement exception handling
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Failed to process the file using single thread executor: " + e.getMessage(), e);
        }
    }

    public void processAndSaveVirtualThread() {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(() -> {
                List<Product> sortedProducts = new ArrayList<>(productQueue);
                productQueue.clear();
                sortedProducts.sort(Comparator.comparing(Product::getId));
                productRepository.saveAll(sortedProducts);
            });
        }
    }

    public void processAndSaveFixedThreadPool() {
        try (var executor = Executors.newFixedThreadPool(10)) { // Same pool size as above
            executor.submit(() -> {
                List<Product> sortedProducts = new ArrayList<>(productQueue);
                productQueue.clear();
                sortedProducts.sort(Comparator.comparing(Product::getId));
                productRepository.saveAll(sortedProducts);
            });
        } catch (Exception e) {
            throw new RuntimeException("Failed to process and save products using fixed thread pool: " + e.getMessage(), e);
        }
    }

    public void processAndSaveSingleThread() {
        try (var executor = Executors.newSingleThreadExecutor()) {
            executor.submit(() -> {
                List<Product> sortedProducts = new ArrayList<>(productQueue);
                productQueue.clear();
                sortedProducts.sort(Comparator.comparing(Product::getId));
                productRepository.saveAll(sortedProducts);
            });
        } catch (Exception e) {
            throw new RuntimeException("Failed to process and save products using single thread executor: " + e.getMessage(), e);
        }
    }

    @Override
    public List <Product> findAll() {
        return productRepository.findAll();
    }
}
