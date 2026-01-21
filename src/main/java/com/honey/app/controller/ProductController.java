package com.honey.app.controller;

import com.honey.app.io.ProductRequest;
import com.honey.app.io.ProductResponse;
import com.honey.app.service.impl.ProductServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.ObjectMapper;

import java.util.List;


@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
@CrossOrigin("*")
public class ProductController {
    private ProductServiceImpl productServiceImpl;
    @PostMapping
    public ProductResponse addProduct(@RequestPart("product") String productString,
                                      @RequestPart("file")MultipartFile file){
        ObjectMapper objectMapper = new ObjectMapper();
        ProductRequest request = null;
        try{
            request = objectMapper.readValue(productString,ProductRequest.class);
        }catch (ResponseStatusException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid JSON format");
        }
        ProductResponse response = productServiceImpl.addProduct(request,file);
        return response;
    }
    @GetMapping
    public List<ProductResponse> readProducts(){
        return productServiceImpl.readProduct();
    }
    @GetMapping("/{id}")
    public ProductResponse readProduct(@PathVariable String id){
        return productServiceImpl.readProduct(id);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable String id){
        productServiceImpl.deleteProduct(id);
    }

}
