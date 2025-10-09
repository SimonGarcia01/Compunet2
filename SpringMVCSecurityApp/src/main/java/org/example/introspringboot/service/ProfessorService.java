package org.example.introspringboot.service;

import org.example.introspringboot.api.v1.dto.ProfessorDTO;
import org.example.introspringboot.entity.Professor;

import java.util.List;
import java.util.Optional;

public interface ProfessorService {
    List<ProfessorDTO> findAll();
    Optional<ProfessorDTO> findById(Integer id);
    ProfessorDTO save(ProfessorDTO professor);
    void deleteById(Integer id);
}
