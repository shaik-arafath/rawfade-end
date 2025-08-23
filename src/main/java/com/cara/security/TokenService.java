package com.cara.security;

import org.springframework.stereotype.Service;

@Service
public class TokenService {
    
    /**
     * Extract username from the simple token format: "token_username_timestamp"
     */
    public String extractUsernameFromToken(String token) {
        if (token == null || !token.startsWith("token_")) {
            return null;
        }
        
        try {
            // Token format: "token_username_timestamp"
            String[] parts = token.split("_");
            if (parts.length >= 3) {
                // Username is the second part (index 1)
                return parts[1];
            }
        } catch (Exception e) {
            return null;
        }
        
        return null;
    }
    
    /**
     * Validate if token is in correct format
     */
    public boolean isValidTokenFormat(String token) {
        if (token == null || !token.startsWith("token_")) {
            return false;
        }
        
        try {
            String[] parts = token.split("_");
            if (parts.length >= 3) {
                // Check if timestamp is a valid number
                Long.parseLong(parts[2]);
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        
        return false;
    }
    
    /**
     * Extract token from Authorization header
     */
    public String extractTokenFromHeader(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return authHeader;
    }
}
