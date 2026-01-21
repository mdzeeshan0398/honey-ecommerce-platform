package com.honey.app.service;

import com.honey.app.io.CartRequest;
import com.honey.app.io.CartResponse;

public interface CartService {
    CartResponse addToCart(CartRequest request);
    CartResponse getCart();
    void deleteCart();
    CartResponse removeFromCart(CartRequest request);
}
