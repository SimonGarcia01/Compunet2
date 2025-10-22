package org.example.introspringboot.api.v1.mappers;

import javax.annotation.processing.Generated;
import org.example.introspringboot.api.v1.dto.ProfessorDTO;
import org.example.introspringboot.entity.Professor;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-22T12:30:03-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class ProfessorMapperImpl implements ProfessorMapper {

    @Override
    public ProfessorDTO toDto(Professor professor) {
        if ( professor == null ) {
            return null;
        }

        ProfessorDTO professorDTO = new ProfessorDTO();

        professorDTO.setId( professor.getId() );
        professorDTO.setName( professor.getName() );

        return professorDTO;
    }

    @Override
    public Professor toEntity(ProfessorDTO professorDTO) {
        if ( professorDTO == null ) {
            return null;
        }

        Professor professor = new Professor();

        if ( professorDTO.getId() != null ) {
            professor.setId( professorDTO.getId() );
        }
        professor.setName( professorDTO.getName() );

        return professor;
    }

    @Override
    public void updateEntityFromDTO(ProfessorDTO professorDTO, Professor professor) {
        if ( professorDTO == null ) {
            return;
        }

        if ( professorDTO.getId() != null ) {
            professor.setId( professorDTO.getId() );
        }
        professor.setName( professorDTO.getName() );
    }
}
