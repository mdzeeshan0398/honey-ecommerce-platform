package com.honey.app.service.impl;

import com.honey.app.entity.Product;
import com.honey.app.io.ProductRequest;
import com.honey.app.io.ProductResponse;
import com.honey.app.repository.ProductRepository;
import com.honey.app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    private S3Client s3Client;
    @Autowired
    private ProductRepository productRepository;
    @Value("${aws.s3.bucketname}")
    private String bucketName;

    public ProductServiceImpl(S3Client s3Client, ProductRepository productRepository) {
        this.s3Client = s3Client;
        this.productRepository = productRepository;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        String fileName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
        String key = UUID.randomUUID().toString()+"."+fileName;
        try{
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .acl("public-read")
                    .contentType(file.getContentType())
                    .build();
            PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
            if(putObjectResponse.sdkHttpResponse().isSuccessful()){
                return "https://"+bucketName+".s3.amazonaws.com/"+key;
            }else{
                throw new  ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"File upload failed");
            }
        }catch(IOException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"An error occurred while uploading the file");
        }
    }

    @Override
    public ProductResponse addProduct(ProductRequest request, MultipartFile file) {
         Product product = convertToEntity(request);
         String imageUrl = uploadFile(file);
         product.setImageUrl(imageUrl);
         product = productRepository.save(product);
         return convertToResponse(product);
    }

    @Override
    public List<ProductResponse> readProduct() {
        List<Product> product = productRepository.findAll();
        return product.stream().map(object -> convertToResponse(object)).collect(Collectors.toList());
    }

    @Override
    public ProductResponse readProduct(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"+id));
        return convertToResponse(product);
     }

    @Override
    public boolean deleteFile(String fileName) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
        return true;
    }

    @Override
    public void deleteProduct(String id) {
        ProductResponse product = readProduct(id);
        String imageUrl = product.getImageUrl();
        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/")+1);
        boolean isDeleted = deleteFile(fileName);
        if(isDeleted){
            productRepository.deleteById(product.getId());
        }

    }
    private Product convertToEntity(ProductRequest request){
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .price(request.getPrice())
                .build();
    }
    private ProductResponse convertToResponse(Product product){
        return  ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .build();
    }
}
