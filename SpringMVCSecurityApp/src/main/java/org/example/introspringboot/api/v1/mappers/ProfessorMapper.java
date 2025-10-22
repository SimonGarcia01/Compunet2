package org.example.introspringboot.api.v1.mappers;

import org.example.introspringboot.api.v1.dto.ProfessorDTO;
import org.example.introspringboot.entity.Professor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProfessorMapper {
    //From Entity to DTO
    public ProfessorDTO toDto(Professor professor);

    //From DTO to Entity
    public Professor toEntity(ProfessorDTO professorDTO);

    //Just added this method to have it
    //Saw it in the course instructions
    public void updateEntityFromDTO(ProfessorDTO professorDTO, @MappingTarget Professor professor);
}
