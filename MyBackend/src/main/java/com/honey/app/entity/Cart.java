package com.honey.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    private String id;
    private String userId;
    @ElementCollection
    private Map<String, Integer> items = new HashMap<>();
    @PrePersist
    public void generateId() {
        this.id = UUID.randomUUID().toString();
    }
    public Cart(String userId,Map<String,Integer> items){
        this.userId = userId;
        this.items = items;
    }
}
