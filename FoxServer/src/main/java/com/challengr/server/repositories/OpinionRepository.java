package com.challengr.server.repositories;

import com.challengr.server.model.clip.Opinion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpinionRepository extends JpaRepository<Opinion, Long> {}
