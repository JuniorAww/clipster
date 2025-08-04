package com.challengr.server.repositories;

import com.challengr.server.model.clip.Clip;
import com.challengr.server.model.user.Credential;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CredentialRepository extends JpaRepository<Credential, Long> {
    @EntityGraph(attributePaths = {"user"})
    Optional<Credential> findByMethodAndMetadata(Credential.Method method, String metadata);
    boolean existsByMethodAndMetadata(Credential.Method method, String metadata);
}

