package com.honey.app.entity;

import com.honey.app.io.OrderItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    private String id;
    private String userId;
    private String userAddress;
    private String phoneNumber;
    private String email;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderItem> orderedItems;
    private double amount;
    private String paymentSuccess;
    private String razorpayOrderId;
    private String razorpaySignature;
    private String orderStatus;
    private String razorpayPaymentId;
    @PrePersist
    public void generateId(){
        this.id = UUID.randomUUID().toString();
    }
}
