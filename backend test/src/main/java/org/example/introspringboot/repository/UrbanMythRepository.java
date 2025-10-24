package org.example.introspringboot.repository;

import org.example.introspringboot.entity.UrbanMyth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrbanMythRepository extends JpaRepository<UrbanMyth, Long> {
}
