package com.woojang.service.ch11ex1s1.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woojang.service.ch11ex1s1.entities.User;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserByUsername(final String username);
}
