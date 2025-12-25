package com.clientpanel.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clientpanel.model.User;
import com.clientpanel.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<User> getUsersByStatus(String status, Pageable pageable) {
        return userRepository.findByStatus(status, pageable);
    }

    public Optional<User> getUserById(String clientId) {
        return userRepository.findById(clientId);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createUser(User user) {
        user.setRole("ROLE_CLIENT");
        user.setStatus("Active");
        user.setPass(passwordEncoder.encode(user.getPass()));
        return userRepository.save(user);
    }

    public User updateUser(String clientId, User userDetails) {
        User user = userRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("User not found with client_id: " + clientId));

        if (userDetails.getEmail() != null) {
            user.setEmail(userDetails.getEmail());
        }
        if (userDetails.getName() != null) {
            user.setName(userDetails.getName());
        }
        if (userDetails.getMobile() != null) {
            user.setMobile(userDetails.getMobile());
        }
        if (userDetails.getPhoneNumberId() != null) {
            user.setPhoneNumberId(userDetails.getPhoneNumberId());
        }
        if (userDetails.getChatPrefix() != null) {
            user.setChatPrefix(userDetails.getChatPrefix());
        }
        if (userDetails.getStatus() != null) {
            user.setStatus(userDetails.getStatus());
        }
        // Password is updated separately through changePassword method

        return userRepository.save(user);
    }

    public void deleteUser(String clientId) {
        userRepository.deleteById(clientId);
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean mobileExists(String mobile) {
        return userRepository.findByMobile(mobile).isPresent();
    }

    public boolean phoneNumberIdExists(String phoneNumberId) {
        return userRepository.findByPhoneNumberId(phoneNumberId).isPresent();
    }

    public long getActiveUsersCount() {
        return userRepository.countByStatus("Active");
    }

    public long getInactiveUsersCount() {
        return userRepository.countByStatus("Inactive");
    }

    public long getTotalUsersCount() {
        return userRepository.count();
    }

    public boolean isEmailUniqueExcluding(String email, String clientId) {
        return userRepository.findByEmail(email)
                .map(user -> user.getClientId().equals(clientId))
                .orElse(true);
    }

    public boolean isMobileUniqueExcluding(String mobile, String clientId) {
        return userRepository.findByMobile(mobile)
                .map(user -> user.getClientId().equals(clientId))
                .orElse(true);
    }

    public boolean isPhoneNumberIdUniqueExcluding(String phoneNumberId, String clientId) {
        return userRepository.findByPhoneNumberId(phoneNumberId)
                .map(user -> user.getClientId().equals(clientId))
                .orElse(true);
    }
}
