package com.example.Lumiere.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Lumiere.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginUser(String loginUser);
    Optional<User> findByMail(String mail);
    boolean existsByLoginUser(String loginUser);
    boolean existsByMail(String mail);
    
    long countByActive(boolean active);
}