package com.cara.config;

import com.cara.entity.User;
import com.cara.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        // Create sample users if they don't exist
        createSampleUsers();
    }

    private void createSampleUsers() {
        // Create arafathshaik user
        if (!userService.existsByUsername("arafathshaik")) {
            User user = new User();
            user.setUsername("arafathshaik");
            user.setEmail("arafath@example.com");
            user.setPassword("password123"); // Plain text for now
            user.setFirstName("Arafath");
            user.setLastName("Shaik");
            user.setPhoneNumber("1234567890");
            user.setAddress("Sample Address");
            user.setEnabled(true);
            
            Set<String> roles = new HashSet<>();
            roles.add("ROLE_USER");
            user.setRoles(roles);
            
            userService.createUser(user);
            System.out.println("Created sample user: arafathshaik");
        }

        // Create admin user
        if (!userService.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@cara.com");
            admin.setPassword("admin123");
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setPhoneNumber("9876543210");
            admin.setAddress("Admin Address");
            admin.setEnabled(true);
            
            Set<String> roles = new HashSet<>();
            roles.add("ROLE_ADMIN");
            roles.add("ROLE_USER");
            admin.setRoles(roles);
            
            userService.createUser(admin);
            System.out.println("Created admin user: admin");
        }

        // Create test user
        if (!userService.existsByUsername("testuser")) {
            User testUser = new User();
            testUser.setUsername("testuser");
            testUser.setEmail("test@example.com");
            testUser.setPassword("test123");
            testUser.setFirstName("Test");
            testUser.setLastName("User");
            testUser.setPhoneNumber("5555555555");
            testUser.setAddress("Test Address");
            testUser.setEnabled(true);
            
            Set<String> roles = new HashSet<>();
            roles.add("ROLE_USER");
            testUser.setRoles(roles);
            
            userService.createUser(testUser);
            System.out.println("Created test user: testuser");
        }
    }
}
