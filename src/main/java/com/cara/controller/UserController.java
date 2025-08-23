package com.cara.controller;

import com.cara.entity.User;
import com.cara.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/verify/{username}")
    public ResponseEntity<Map<String, Object>> verifyUser(@PathVariable String username) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<User> userOpt = userService.findByUsername(username);
            
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                response.put("exists", true);
                response.put("username", user.getUsername());
                response.put("email", user.getEmail());
                response.put("id", user.getId());
                response.put("enabled", user.isEnabled());
            } else {
                response.put("exists", false);
                response.put("message", "User not found");
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("exists", false);
            response.put("error", "Error checking user: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // For debugging - list all usernames
            response.put("message", "Check server logs for user list");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("error", "Error listing users: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
