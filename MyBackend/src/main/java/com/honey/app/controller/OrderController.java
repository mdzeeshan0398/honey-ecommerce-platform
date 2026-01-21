package com.honey.app.controller;

import com.honey.app.io.OrderRequest;
import com.honey.app.io.OrderResponse;
import com.honey.app.service.impl.OrderServiceImpl;
import com.razorpay.RazorpayException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrderResponse(@RequestBody OrderRequest request)throws RazorpayException {
        OrderResponse response = orderService.createOrderWithPayment(request);
        return response;
    }
    @PostMapping("/verify")
    public void verifyPayment(@RequestBody Map<String,String> paymentData){
        orderService.verifyPayment(paymentData,"Paid");
    }
    @GetMapping
    public List<OrderResponse> getOrders(){
        return orderService.getUserOrders();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("id") String orderId){
        orderService.removeOrder(orderId);
    }
    @GetMapping("/all")
    public List<OrderResponse> getAllOrders(){
        return orderService.getOrdersOfAllUsers();
    }
    @PatchMapping("/status/{id}")
    public void updateOrderStatus(@PathVariable("id") String orderId,@RequestParam String status){
        orderService.updateOrderStatus(orderId,status);
    }
}
