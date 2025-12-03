package org.example.finalproject.service.impl;

import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.example.finalproject.api.v1.dtos.UserRoleRequestResponse;
import org.example.finalproject.api.v1.dtos.UserWorkoutProgramRequestResponse;
import org.example.finalproject.api.v1.mappers.*;
import org.example.finalproject.entity.*;
import org.example.finalproject.exceptions.MissingInfoException;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.repository.UserRoleRepository;
import org.example.finalproject.repository.UserWorkoutProgramRepository;
import org.example.finalproject.service.UserRoleService;
import org.example.finalproject.service.UserWorkoutProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserWorkoutProgramServiceImpl implements UserWorkoutProgramService {

    // Beans
    @Autowired
    private UserWorkoutProgramRepository userWorkoutProgramRepository;
    @Autowired
    private UserWorkoutProgramMapper userWorkoutProgramMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WorkoutProgramMapper workoutProgramMapper;


    // Find user workout program by IDs
    @Override
    public UserWorkoutProgramRequestResponse findById(Integer userId, Integer workoutId) {

        //Make the id so then it can be used to look for it
        UserWorkoutId id = new UserWorkoutId();
        id.setUserId(userId);
        id.setWorkoutId(workoutId);

        return userWorkoutProgramRepository.findById(id)
                .map(userWorkoutProgramMapper::toUserWorkoutProgramResponse)
                .orElseThrow(() -> new ResourceNotFoundException("User Workout Program not found with id: " + id));
    }

    // Get all user workout programs
    @Override
    public List<UserWorkoutProgramRequestResponse> getAllUserWorkoutPrograms() {

        return userWorkoutProgramRepository.findAll().stream()
                .map(userWorkoutProgramMapper::toUserWorkoutProgramResponse)
                .toList();

    }

    // Create a new user workout program
    @Override
    @Transactional
    public void createUserWorkoutProgram(UserWorkoutProgramRequestResponse request) {

        if (request.getUser() == null || request.getWorkoutProgram() == null) {
            throw new MissingInfoException("One or more fields were not filled. Try again.");
        }

        // If the program stay in this point is because the request have all the attributes for create != null
        UserWorkoutProgram userWorkoutProgram = userWorkoutProgramMapper.toUserWorkoutProgram(request);

        userWorkoutProgramRepository.save(userWorkoutProgram);

    }

    // Update an existing user workout program
    @Override
    @Transactional
    public void updateUserWorkoutProgram(Integer userId, Integer workoutId, UserWorkoutProgramRequestResponse request) {

        //Make the id so then it can be used to look for it
        UserWorkoutId id = new UserWorkoutId();
        id.setUserId(userId);
        id.setWorkoutId(workoutId);

        UserWorkoutProgram userWorkoutProgram = userWorkoutProgramRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Workout Program not found with id: " + id));

        if (request.getUser() != null) {
            userWorkoutProgram.setUser(userMapper.toUser(request.getUser()));
        }

        if (request.getWorkoutProgram() != null) {
            userWorkoutProgram.setWorkoutProgram(workoutProgramMapper.toWorkoutProgram(request.getWorkoutProgram()));
        }

        userWorkoutProgramRepository.save(userWorkoutProgram);

    }

    // Delete a user workout program
    @Override
    public void deleteUserWorkoutProgram(Integer userId, Integer workoutId) {

        //Make the id so then it can be used to look for it
        UserWorkoutId id = new UserWorkoutId();
        id.setUserId(userId);
        id.setWorkoutId(workoutId);

        userWorkoutProgramRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Workout Program not found with id: " + id));

        userWorkoutProgramRepository.deleteById(id);

    }
}
