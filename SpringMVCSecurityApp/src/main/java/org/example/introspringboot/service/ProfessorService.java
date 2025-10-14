package org.example.introspringboot.service;

import org.example.introspringboot.api.v1.dto.ProfessorDTO;

import java.util.List;

public interface ProfessorService {
    List<ProfessorDTO> findAll();
    ProfessorDTO findById(Integer id);
    ProfessorDTO save(ProfessorDTO professor);
    void deleteById(Integer id);
}
