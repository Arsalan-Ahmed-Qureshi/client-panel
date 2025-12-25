package com.clientpanel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "client_id", length = 10)
    @NotBlank(message = "Client ID is required")
    @Size(min = 3, max = 10, message = "Client ID must be between 3 and 10 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Client ID must contain only alphanumeric, underscore, and hyphen characters")
    private String clientId;

    @Column(name = "email", unique = true, nullable = false, length = 255)
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Column(name = "name", length = 255)
    @NotBlank(message = "Name is required")
    private String name;

    @Column(name = "mobile", unique = true, length = 20)
    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Mobile number must be numeric between 10 and 15 digits")
    private String mobile;

    @Column(name = "phone_number_id", unique = true, length = 15)
    @NotBlank(message = "Phone number ID is required")
    @Pattern(regexp = "^[0-9]{15}$", message = "Phone number ID must be exactly 15 numeric digits")
    private String phoneNumberId;

    @Column(name = "pass", length = 255, nullable = false)
    @NotBlank(message = "Password is required")
    private String pass;

    @Column(name = "chat_prefix", columnDefinition = "TEXT")
    @Size(max = 2000, message = "Chat prefix cannot exceed 2000 characters")
    private String chatPrefix;

    @Column(name = "role", length = 50)
    private String role;

    @Column(name = "status", length = 20)
    @Builder.Default
    private String status = "Active";

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.updatedAt == null) {
            this.updatedAt = LocalDateTime.now();
        }
        if (this.role == null) {
            this.role = "ROLE_CLIENT";
        }
        if (this.status == null) {
            this.status = "Active";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
