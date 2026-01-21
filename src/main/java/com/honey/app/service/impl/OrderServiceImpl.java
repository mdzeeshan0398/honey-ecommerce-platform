package com.honey.app.service.impl;

import com.honey.app.entity.Order;
import com.honey.app.io.OrderRequest;
import com.honey.app.io.OrderResponse;
import com.honey.app.repository.CartRepository;
import com.honey.app.repository.OrderRepository;
import com.honey.app.service.OrderService;
import com.honey.app.service.UserService;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private UserService userService;
    private CartRepository cartRepository;
    @Value("${razorpay_key}")
    private String RAZORPAY_KEY;
    @Value("${razorpay_secret}")
    private String RAZORPAY_SECRET;

    public OrderServiceImpl(OrderRepository orderRepository, UserService userService,CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.cartRepository = cartRepository;
    }

    @Override
    public OrderResponse createOrderWithPayment(OrderRequest request) throws RazorpayException {
        Order newOrder = convertToEntity(request);
        newOrder = orderRepository.save(newOrder);
        RazorpayClient razorpayClient = new RazorpayClient(RAZORPAY_KEY,RAZORPAY_SECRET);
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount",(int)newOrder.getAmount() * 100);
        orderRequest.put("currency","INR");
        orderRequest.put("receipt","rcpt_"+newOrder.getId().substring(0,20));
        com.razorpay.Order razorpayOrder = razorpayClient.orders.create(orderRequest);
        newOrder.setRazorpayOrderId(razorpayOrder.get("id"));
        String loggedInUserId = userService.findByUserId();
        newOrder.setUserId(loggedInUserId);
        newOrder = orderRepository.save(newOrder);
        return convertToResponse(newOrder);
    }

    @Override
    @Transactional
    public void verifyPayment(Map<String, String> payment, String status) {
        String razorpayOrder_id = payment.get("razorpay_order_id");
         Order order = orderRepository.findByRazorpayOrderId(razorpayOrder_id).orElseThrow(() -> new RuntimeException("Order not found"));
         order.setPaymentSuccess(status);
         order.setRazorpaySignature(payment.get("razorpay_signature"));
         order.setRazorpayPaymentId(payment.get("razorpay_payment_id"));
         orderRepository.save(order);
         if("Paid".equalsIgnoreCase(status)){
             cartRepository.deleteByUserId(order.getUserId());
         }
    }

    @Override
    public List<OrderResponse> getUserOrders() {
        String loggedInUserId = userService.findByUserId();
        List<Order> orderList = orderRepository.findByUserId(loggedInUserId);
        return orderList.stream().map(entity -> convertToResponse(entity)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void removeOrder(String orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<OrderResponse> getOrdersOfAllUsers() {
        List<Order> listOrder = orderRepository.findAll();
        return listOrder.stream().map((entity) -> convertToResponse(entity)).collect(Collectors.toList());
    }

    @Override
    public void updateOrderStatus(String orderId, String status) {
        Order orderStatus = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        orderStatus.setOrderStatus(status);
        orderRepository.save(orderStatus);
    }

    private Order convertToEntity(OrderRequest request){
        return Order.builder()
                .userAddress(request.getUserAddress())
                .amount(request.getAmount())
                .orderedItems(request.getOrderedItems())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .orderStatus(request.getOrderStatus())
                .build();
    }
    private OrderResponse  convertToResponse(Order newOrder){
        return  OrderResponse.builder()
                .id(newOrder.getId())
                .amount(newOrder.getAmount())
                .userAddress(newOrder.getUserAddress())
                .userId(newOrder.getUserId())
                .razorpayOrderId(newOrder.getRazorpayOrderId())
                .paymentSuccess(newOrder.getPaymentSuccess())
                .orderStatus(newOrder.getOrderStatus())
                .email(newOrder.getEmail())
                .phoneNumber(newOrder.getPhoneNumber())
                .orderItems(newOrder.getOrderedItems())
                .build();
    }
}
