package com.challengr.server.repositories;

import com.challengr.server.model.clip.Resolution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResolutionRepository extends JpaRepository<Resolution, Long> {}
