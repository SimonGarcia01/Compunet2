package org.example.introspringboot.api.mappers;

import org.example.introspringboot.api.dto.UrbanMythRequest;
import org.example.introspringboot.api.dto.UrbanMythResponse;
import org.example.introspringboot.entity.UrbanMyth;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UrbanMythMapper {
    public UrbanMythResponse toDto(UrbanMyth urbanMyth);
    public UrbanMyth toEntity(UrbanMythRequest urbanMythDTO);
}
