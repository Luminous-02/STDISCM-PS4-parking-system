package com.parkingsystem.auth.config;

import com.parkingsystem.auth.model.User;
import com.parkingsystem.auth.model.UserRole;
import com.parkingsystem.auth.repository.UserRepository;
import com.parkingsystem.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Create sample users if none exist
        if (userRepository.count() == 0) {
            createSampleUsers();
        }
    }

    private void createSampleUsers() {
        try {
            // Student user
            userService.registerUser("student1", "password123", "student1@university.edu", UserRole.STUDENT);
            
            // Faculty user  
            userService.registerUser("faculty1", "password123", "professor@university.edu", UserRole.FACULTY);
            
            // Admin user
            userService.registerUser("admin", "admin123", "admin@university.edu", UserRole.ADMIN);
            
            System.out.println("Sample users created successfully!");
        } catch (Exception e) {
            System.out.println("Sample users already exist: " + e.getMessage());
        }
    }
}