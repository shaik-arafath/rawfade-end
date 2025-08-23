// This file has been disabled to fix compilation errors
// Original content moved to CartController.java.bak

package com.cara.controller;

import com.cara.entity.Cart;
import com.cara.entity.Product;
import com.cara.entity.User;
import com.cara.security.TokenService;
import com.cara.service.CartService;
import com.cara.service.ProductService;
import com.cara.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartService cartService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private TokenService tokenService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addToCart(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") Integer quantity) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Extract token from Authorization header
            String token = tokenService.extractTokenFromHeader(authHeader);
            if (token == null || !tokenService.isValidTokenFormat(token)) {
                response.put("success", false);
                response.put("message", "Invalid or missing authorization token");
                return ResponseEntity.status(401).body(response);
            }
            
            // Extract username from token
            String username = tokenService.extractUsernameFromToken(token);
            if (username == null) {
                response.put("success", false);
                response.put("message", "Unable to extract user from token");
                return ResponseEntity.status(401).body(response);
            }
            
            // Find user
            Optional<User> userOpt = userService.findByUsername(username);
            if (!userOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "User not found: " + username);
                response.put("debug", "Token extracted username: " + username);
                return ResponseEntity.badRequest().body(response);
            }
            
            // Find product
            Optional<Product> productOpt = productService.getProductById(productId);
            if (!productOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Product not found");
                return ResponseEntity.badRequest().body(response);
            }
            
            User user = userOpt.get();
            
            // Add item to cart using userId
            cartService.addItemToCart(user.getId(), productId, quantity);
            
            response.put("success", true);
            response.put("message", "Product added to cart successfully");
            response.put("productId", productId);
            response.put("quantity", quantity);
            response.put("username", username);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error adding product to cart: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // Keep the old endpoint for backward compatibility but mark as deprecated
    @PostMapping("/user/{username}/add")
    @Deprecated
    public ResponseEntity<Map<String, Object>> addToCartLegacy(
            @PathVariable String username,
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") Integer quantity) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Find user
            Optional<User> userOpt = userService.findByUsername(username);
            if (!userOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "User not found");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Find product
            Optional<Product> productOpt = productService.getProductById(productId);
            if (!productOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Product not found");
                return ResponseEntity.badRequest().body(response);
            }
            
            User user = userOpt.get();
            
            // Add item to cart using userId
            cartService.addItemToCart(user.getId(), productId, quantity);
            
            response.put("success", true);
            response.put("message", "Product added to cart successfully");
            response.put("productId", productId);
            response.put("quantity", quantity);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error adding product to cart: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/my-cart")
    public ResponseEntity<Map<String, Object>> getMyCart(@RequestHeader("Authorization") String authHeader) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Extract token from Authorization header
            String token = tokenService.extractTokenFromHeader(authHeader);
            if (token == null || !tokenService.isValidTokenFormat(token)) {
                response.put("success", false);
                response.put("message", "Invalid or missing authorization token");
                return ResponseEntity.status(401).body(response);
            }
            
            // Extract username from token
            String username = tokenService.extractUsernameFromToken(token);
            if (username == null) {
                response.put("success", false);
                response.put("message", "Unable to extract user from token");
                return ResponseEntity.status(401).body(response);
            }
            
            Optional<User> userOpt = userService.findByUsername(username);
            if (!userOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "User not found");
                return ResponseEntity.badRequest().body(response);
            }
            
            Cart cart = cartService.getCartByUserId(userOpt.get().getId());
            response.put("success", true);
            response.put("cart", cart);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error retrieving cart: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<Cart> getCart(@PathVariable String username) {
        try {
            Optional<User> userOpt = userService.findByUsername(username);
            if (!userOpt.isPresent()) {
                return ResponseEntity.badRequest().build();
            }
            
            Cart cart = cartService.getCartByUserId(userOpt.get().getId());
            return ResponseEntity.ok(cart);
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
