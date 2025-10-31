// GeneralProgressRepository.java
package org.example.finalproject.repository;

import org.example.finalproject.entity.GeneralProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralProgressRepository extends JpaRepository<GeneralProgress, Integer> {}
