// GeneralProgressRepository.java
package org.example.taller2springboot.repository;

import org.example.taller2springboot.entity.GeneralProgress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneralProgressRepository extends JpaRepository<GeneralProgress, Integer> {}
