// RecommendationRepository.java
package org.example.taller2springboot.repository;

import org.example.taller2springboot.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationRepository extends JpaRepository<Recommendation, Integer> {}
