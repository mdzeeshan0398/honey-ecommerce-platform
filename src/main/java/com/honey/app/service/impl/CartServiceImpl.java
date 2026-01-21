package com.honey.app.service.impl;

import com.honey.app.entity.Cart;
import com.honey.app.io.CartRequest;
import com.honey.app.io.CartResponse;
import com.honey.app.repository.CartRepository;
import com.honey.app.service.CartService;
import com.honey.app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;
    private UserService userService;
    @Override
    public CartResponse addToCart(CartRequest request) {
        String loggedInUserId = userService.findByUserId();
        Optional<Cart> cartOptional = cartRepository.findByUserId(loggedInUserId);
        Cart newCart = cartOptional.orElseGet(() -> new Cart(loggedInUserId,new HashMap<>()));
        Map<String,Integer> items = newCart.getItems();
        items.put(request.getProductId(),items.getOrDefault(request.getProductId(),0) + 1);
        newCart.setItems(items);
        Cart cart = cartRepository.save(newCart);
        return convertToResponse(cart);
    }

    @Override
    public CartResponse getCart() {
        String loggedInUserId = userService.findByUserId();
        Cart cart = cartRepository.findByUserId(loggedInUserId).orElse(new Cart(null,loggedInUserId,new HashMap<>()));
        return convertToResponse(cart);
    }

    @Override
    public void deleteCart() {
        String loggedInUserId = userService.findByUserId();
        cartRepository.deleteByUserId(loggedInUserId);
    }

    @Override
    public CartResponse removeFromCart(CartRequest request) {
        String loggedInUserId = userService.findByUserId();
        Cart cart = cartRepository.findByUserId(loggedInUserId).orElseThrow(() -> new RuntimeException("Cart is not found"));
        Map<String, Integer> cartItems = cart.getItems();
        if(cartItems.containsKey(request.getProductId())){
            int qty = cartItems.get(request.getProductId());
            if(qty > 0){
                cartItems.put(request.getProductId(),qty - 1);
            }else{
                cartItems.remove(request.getProductId());
            }
            cart = cartRepository.save(cart);
        }
        return convertToResponse(cart);

    }

    private CartResponse convertToResponse(Cart cart){
        return CartResponse.builder()
                .id(cart.getId())
                .userId(cart.getUserId())
                .items(cart.getItems())
                .build();
    }
}
