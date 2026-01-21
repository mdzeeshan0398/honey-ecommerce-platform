package com.honey.app.controller;

import com.honey.app.io.CartRequest;
import com.honey.app.io.CartResponse;
import com.honey.app.service.impl.CartServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@AllArgsConstructor
public class CartController {

    private CartServiceImpl cartService;

    @PostMapping
    public CartResponse addToCart(@RequestBody CartRequest request){
        String productId = request.getProductId();
        if(productId == null || productId.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Product id not found");

        }
        return cartService.addToCart(request);
    }@GetMapping
    public CartResponse getCart(){
        return cartService.getCart();
    }
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart(){
        cartService.deleteCart();
    }
    @PostMapping("/remove")
    public CartResponse removeFromCart(@RequestBody CartRequest request){
        String productId = request.getProductId();
        if(productId == null || productId.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Product id not found");

        }
        return cartService.removeFromCart(request);
    }
}
