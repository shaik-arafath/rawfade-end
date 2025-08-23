package com.cara.service;

import com.cara.entity.User;
import com.cara.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    // Temporarily disabled password encoding for minimal setup
    // @Autowired
    // private PasswordEncoder passwordEncoder;
    
    public User createUser(User user) {
        // Set default role if none provided
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            Set<String> roles = new HashSet<>();
            roles.add("ROLE_USER");
            user.setRoles(roles);
        }
        
        // Temporarily store password as plain text for minimal setup
        // user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        
        return userRepository.save(user);
    }
    
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}

