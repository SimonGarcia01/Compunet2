package org.example.introspringboot.service.impl;

import org.example.introspringboot.api.v1.dto.ProfessorDTO;
import org.example.introspringboot.entity.Professor;
import org.example.introspringboot.repository.ProfessorRepository;
import org.example.introspringboot.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.stream;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Override
    public List<ProfessorDTO> findAll() {
        //Pass every professor from the repository into the DTO professor
        return professorRepository.findAll().stream().map(
                professor -> new ProfessorDTO(
                        professor.getId(),
                        professor.getName()
                )
        ).toList();
    }

    @Override
    public Optional<ProfessorDTO> findById(Integer id) {
        return professorRepository.findById(id);
    }

    @Override
    public ProfessorDTO save(ProfessorDTO professor) {
        return professorRepository.save(professor);
    }

    @Override
    public void deleteById(Integer id) {
        professorRepository.deleteById(id);
    }
}
