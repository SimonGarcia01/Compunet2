package org.example.finalproject.api.v1.mappers;

import org.example.finalproject.api.v1.dtos.EventTypeRequest;
import org.example.finalproject.api.v1.dtos.EventTypeResponse;
import org.example.finalproject.entity.EventType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventTypeMapper {

    EventTypeResponse toEventTypeResponse(EventType eventType);

    EventType toEventTypeEntity(EventTypeRequest eventTypeRequest);
}

