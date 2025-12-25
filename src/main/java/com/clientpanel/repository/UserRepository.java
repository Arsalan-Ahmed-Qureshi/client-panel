package com.clientpanel.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clientpanel.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByMobile(String mobile);
    Optional<User> findByPhoneNumberId(String phoneNumberId);
    Page<User> findByStatus(String status, Pageable pageable);
    Page<User> findAll(Pageable pageable);
    long countByStatus(String status);
}
