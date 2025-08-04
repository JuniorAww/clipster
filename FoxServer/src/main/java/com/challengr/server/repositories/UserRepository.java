package com.challengr.server.repositories;

import com.challengr.server.model.user.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {"credentials"})
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}

