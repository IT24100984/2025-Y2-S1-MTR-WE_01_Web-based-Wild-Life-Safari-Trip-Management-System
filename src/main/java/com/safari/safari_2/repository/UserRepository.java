// src/main/java/com/safari/safari_2/repository/UserRepository.java
package com.safari.safari_2.repository;

import com.safari.safari_2.enums.Role;
import com.safari.safari_2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByNic(String nic);
    long countByRole(Role role);
}