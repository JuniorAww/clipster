package com.challengr.server.repositories;

import com.challengr.server.model.clip.Clip;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClipRepository extends JpaRepository<Clip, Long> {
    @EntityGraph(attributePaths = {"resolutions"})
    Optional<Clip> findById(Long id);
}

