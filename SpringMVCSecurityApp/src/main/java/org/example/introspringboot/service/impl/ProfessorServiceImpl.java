package org.example.introspringboot.service.impl;

import org.example.introspringboot.api.v1.dto.ProfessorDTO;
import org.example.introspringboot.api.v1.mappers.ProfessorMapper;
import org.example.introspringboot.repository.ProfessorRepository;
import org.example.introspringboot.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ProfessorMapper professorMapper;

    @Override
    public List<ProfessorDTO> findAll() {
        //Pass every professor from the repository into the DTO professor
        //Version without Mapper
//        return professorRepository.findAll().stream().map(
//                professor -> new ProfessorDTO(
//                        professor.getId(),
//                        professor.getName()
//                )
//        ).toList();
        //version with mapper
        return professorRepository.findAll().stream().map(
                professor -> professorMapper.toDto(professor)
        ).toList();
    }

    @Override
    public ProfessorDTO findById(Integer id) {
        //If the professor is not found, returns null
        return professorRepository.findById(id).
                map( professor -> professorMapper.toDto(professor)).orElse(null);
    }

    @Override
    public ProfessorDTO save(ProfessorDTO professorDTO) {
        //Make the entity from the professor DTO that is coming in
        var entity = professorMapper.toEntity(professorDTO);
        //Save the entity within the repository
        professorRepository.save(entity);
        //To return the DTO correctly the ID must be the same to the one the entity
        //Was created with
        professorDTO.setId(entity.getId());

        return professorDTO;
    }

    //This method stay the same
    @Override
    public void deleteById(Integer id) {
        professorRepository.deleteById(id);
    }
}
