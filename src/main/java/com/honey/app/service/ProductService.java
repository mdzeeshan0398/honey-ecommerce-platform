package com.honey.app.service;

import com.honey.app.io.ProductRequest;
import com.honey.app.io.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    String uploadFile(MultipartFile file);
    ProductResponse addProduct(ProductRequest request, MultipartFile file);
    List<ProductResponse> readProduct();
    ProductResponse readProduct(String id);
    boolean deleteFile(String fileName);
    void deleteProduct(String id);
}
