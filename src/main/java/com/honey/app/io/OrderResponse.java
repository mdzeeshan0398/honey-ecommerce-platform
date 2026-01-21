package com.honey.app.io;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    @Id
    private String id;
    private String userId;
    private String userAddress;
    private String phoneNumber;
    private String email;
    private double amount;
    private String razorpayOrderId;
    private String paymentSuccess;
    private String orderStatus;
    private List<OrderItem> orderItems;
}
