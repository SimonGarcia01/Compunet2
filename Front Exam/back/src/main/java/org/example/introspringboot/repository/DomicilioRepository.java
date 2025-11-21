package org.example.introspringboot.repository;

import org.example.introspringboot.entity.Domicilio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DomicilioRepository extends JpaRepository<Domicilio, Long> {
    List<Domicilio> findByUserId(Long userId);
}
