package org.example.finalproject.service;

import org.example.finalproject.api.v1.dtos.MessageRequest;
import org.example.finalproject.api.v1.dtos.MessageResponse;
import org.example.finalproject.api.v1.dtos.UserWorkoutProgramRequestResponse;
import org.example.finalproject.entity.UserWorkoutId;

import java.util.List;

public interface UserWorkoutProgramService {

    UserWorkoutProgramRequestResponse findById(Integer userId, Integer workoutId);
    List<UserWorkoutProgramRequestResponse> getAllUserWorkoutPrograms();
    void createUserWorkoutProgram(UserWorkoutProgramRequestResponse request);
    void updateUserWorkoutProgram(Integer userId, Integer workoutId, UserWorkoutProgramRequestResponse request);
    void deleteUserWorkoutProgram(Integer userId, Integer workoutId);

}
