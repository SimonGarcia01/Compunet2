// RecommendationRepository.java
package org.example.taller3mvc.repository;

import org.example.taller3mvc.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationRepository extends JpaRepository<Recommendation, Integer> {}
