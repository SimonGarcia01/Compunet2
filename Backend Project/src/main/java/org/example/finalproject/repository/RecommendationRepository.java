// RecommendationRepository.java
package org.example.finalproject.repository;

import org.example.finalproject.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Integer> {}
