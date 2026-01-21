package com.honey.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @Column(updatable = false, nullable = false)
    private String id;
    private String name;
    private String description;
    private double price;
    private String category;
    @Column(name = "image_url")
    private String imageUrl;
    @PrePersist
    public void generateId() {
        this.id = UUID.randomUUID().toString();
    }
}
