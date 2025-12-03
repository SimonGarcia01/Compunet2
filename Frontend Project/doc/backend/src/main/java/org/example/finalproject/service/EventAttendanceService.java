package org.example.finalproject.service;

import org.example.finalproject.api.v1.dtos.EventAttendanceRequest;
import org.example.finalproject.api.v1.dtos.EventAttendanceResponse;

import java.util.List;

public interface EventAttendanceService {
    EventAttendanceResponse findById(Integer userId, Integer eventId);
    List<EventAttendanceResponse> getAllEventAttendances();
    void createEventAttendance(EventAttendanceRequest request);
    void deleteEventAttendance(Integer userId, Integer eventId);
    //I think in this case updating doesn't really make sense... just make another one
}
