package org.example.finalproject.api.v1.mappers;

import org.example.finalproject.api.v1.dtos.AvailableSpaceRequest;
import org.example.finalproject.api.v1.dtos.AvailableSpaceResponse;
import org.example.finalproject.entity.AvailableSpace;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AvailableSpaceMapper {
    public AvailableSpace toAvailableSpace(AvailableSpaceRequest availableSpaceRequest);
    public AvailableSpaceResponse toAvailableSpaceResponse(AvailableSpace availableSpace);
}
