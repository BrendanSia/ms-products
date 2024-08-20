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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

@Service
public class CsvServiceImpl implements CsvService {
    @Autowired
    ProductRepository productRepository;
    private final ConcurrentLinkedQueue<Product> productQueue = new ConcurrentLinkedQueue<>();
    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
    public void save(MultipartFile file) {
        executorService.submit(() -> {
            try {
                List<Product> productList = CsvUtil.csvToStuList(file.getInputStream());
                productQueue.addAll(productList);
            } catch (IOException ex) {
                throw new RuntimeException("Data is not store successfully: " + ex.getMessage());
            }
        });
    }

    @Async
    public CompletableFuture<Void> saveAsync(MultipartFile file) {
        return CompletableFuture.runAsync(() -> {
            try {
                List<Product> productList = CsvUtil.csvToStuList(file.getInputStream());
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
                    List<Product> productList = CsvUtil.csvToStuList(file.getInputStream());
                    productQueue.addAll(productList);
                } catch (IOException ex) {
                    throw new RuntimeException("Data is not store successfully: " + ex.getMessage());  // to implement exception handling
                }
            });
        }
    }

    // virtual thread
    public void processAndSave() {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(() -> {
                List<Product> sortedProducts = new ArrayList<>(productQueue);
                productQueue.clear();
                sortedProducts.sort(Comparator.comparing(Product::getId));
                productRepository.saveAll(sortedProducts);
            });
        }
    }

    @Override
    public List <Product> findAll() {
        return productRepository.findAll();
    }
}
