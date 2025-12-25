package com.clientpanel.config;

import com.clientpanel.model.User;
import com.clientpanel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initializeData(UserRepository userRepository) {
        return args -> {
            // Only add test user if database is empty
            if (userRepository.count() == 0) {
                User testUser = User.builder()
                        .clientId("test001")
                        .email("admin@gmail.com")
                        .name("Admin User")
                        .mobile("1234567890")
                        .phoneNumberId("123456789012345")
                        .pass(passwordEncoder.encode("admin"))
                        .chatPrefix("Welcome to our service!")
                        .role("ROLE_ADMIN")
                        .status("Active")
                        .build();

                userRepository.save(testUser);
                System.out.println("\n========================================");
                System.out.println("TEST USER CREATED SUCCESSFULLY");
                System.out.println("========================================");
                System.out.println("Email: admin@gmail.com");
                System.out.println("Password: admin");
                System.out.println("========================================\n");
            }
        };
    }
}
