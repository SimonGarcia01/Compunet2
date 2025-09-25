package org.example.introspringboot.service;

import org.example.introspringboot.entity.Professor;

import java.util.List;
import java.util.Optional;

public interface ProfessorService {
    List<Professor> findAll();
    Optional<Professor> findById(Integer id);
    Professor save(Professor professor);
    void deleteById(Integer id);
}
