package org.example.finalproject.api.v1.mappers;

import org.example.finalproject.api.v1.dtos.EventRequest;
import org.example.finalproject.api.v1.dtos.EventResponse;
import org.example.finalproject.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, AvailableSpaceMapper.class, EventTypeMapper.class})
public interface EventMapper {

    EventResponse toEventResponse(Event event);

    // From request DTO -> entity (IDs will be resolved later in service)
    @Mapping(source ="userId", target="user", ignore = true)
    @Mapping(source ="availableSpaceId",target = "availableSpace", ignore = true)
    @Mapping(source ="eventTypeId", target = "eventType", ignore = true)
    Event toEvent(EventRequest request);
}
