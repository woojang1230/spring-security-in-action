package com.woojang.service.ch10ex3.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woojang.service.ch10ex3.entities.Token;

public interface JpaTokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findTokenByIdentifier(final String identifier);
}
