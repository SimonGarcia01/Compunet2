package org.example.finalproject.api.v1.mappers;

import org.example.finalproject.api.v1.dtos.EventAttendanceRequest;
import org.example.finalproject.api.v1.dtos.EventAttendanceResponse;
import org.example.finalproject.entity.EventAttendance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, EventMapper.class})
public interface EventAttendanceMapper {

    EventAttendanceResponse toEventAttendanceResponse(EventAttendance attendance);

    //Everything will be solved within the service so...
    //This is technically useless but I made it anyway hehe
    @Mapping(source="userId", target="user", ignore = true)
    @Mapping(source="eventId", target = "event", ignore = true)
    EventAttendance toEventAttendance(EventAttendanceRequest request);
}

