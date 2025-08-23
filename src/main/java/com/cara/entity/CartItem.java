package com.cara.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "cart_items")
public class CartItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;
    
    // Constructors
    public CartItem() {}
    
    public CartItem(Cart cart, Product product, Integer quantity, BigDecimal unitPrice) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
    
    // Business methods
    public BigDecimal getTotalPrice() {
        return unitPrice != null && quantity != null ? 
            unitPrice.multiply(BigDecimal.valueOf(quantity)) : BigDecimal.ZERO;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Cart getCart() {
        return cart;
    }
    
    public void setCart(Cart cart) {
        this.cart = cart;
    }
    
    public Product getProduct() {
        return product;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    // Additional methods for service compatibility
    public Long getProductId() {
        return product != null ? product.getId() : null;
    }
    
    public BigDecimal getPrice() {
        return this.unitPrice;
    }
    
    public void setPrice(BigDecimal price) {
        this.unitPrice = price;
    }
    
    public void setTotalPrice(BigDecimal totalPrice) {
        // Calculate unit price from total and quantity
        if (quantity != null && quantity > 0) {
            this.unitPrice = totalPrice.divide(BigDecimal.valueOf(quantity), 2, RoundingMode.HALF_UP);
        }
    }
}
