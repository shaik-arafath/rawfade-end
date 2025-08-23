// This file has been disabled to fix compilation errors
// Original content moved to CartService.java.bak

package com.cara.service;

import com.cara.entity.Cart;
import com.cara.entity.CartItem;
import com.cara.entity.Product;
import com.cara.repository.CartRepository;
import com.cara.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart getCartByUserId(Long userId) {
        Optional<Cart> cartOpt = cartRepository.findByUserId(userId);
        if (cartOpt.isPresent()) {
            return cartOpt.get();
        } else {
            // Create new cart if doesn't exist
            Cart newCart = new Cart(userId);
            return cartRepository.save(newCart);
        }
    }

    public Cart addItemToCart(Long userId, Long productId, int quantity) {
        Cart cart = getCartByUserId(userId);
        
        // Get product details
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if item already exists in cart
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct() != null && item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            // Update quantity if item exists
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            item.setUnitPrice(product.getPrice());
        } else {
            // Add new item to cart
            CartItem newItem = new CartItem();
            newItem.setProduct(product);  // Set the full Product entity
            newItem.setQuantity(quantity);
            newItem.setUnitPrice(product.getPrice());
            cart.addItem(newItem);
        }

        return cartRepository.save(cart);
    }

    public Cart updateCartItemQuantity(Long userId, Long productId, int quantity) {
        Cart cart = getCartByUserId(userId);
        
        CartItem item = cart.getItems().stream()
                .filter(cartItem -> cartItem.getProduct() != null && cartItem.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        if (quantity <= 0) {
            cart.removeItem(item);
        } else {
            item.setQuantity(quantity);
        }

        return cartRepository.save(cart);
    }

    public Cart removeItemFromCart(Long userId, Long productId) {
        Cart cart = getCartByUserId(userId);
        
        CartItem item = cart.getItems().stream()
                .filter(cartItem -> cartItem.getProduct() != null && cartItem.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        cart.removeItem(item);
        return cartRepository.save(cart);
    }

    public void clearCart(Long userId) {
        Cart cart = getCartByUserId(userId);
        cart.clearCart();
        cartRepository.save(cart);
    }

    public BigDecimal getCartTotal(Long userId) {
        Cart cart = getCartByUserId(userId);
        return cart.getTotalAmount();
    }

    public int getCartItemCount(Long userId) {
        Cart cart = getCartByUserId(userId);
        return cart.getTotalItems();
    }
}
