package org.example.finalproject.service.impl;

import jakarta.transaction.Transactional;
import org.example.finalproject.api.v1.dtos.TrainerTraineeRequest;
import org.example.finalproject.api.v1.dtos.TrainerTraineeResponse;
import org.example.finalproject.api.v1.mappers.TrainerTraineeMapper;
import org.example.finalproject.api.v1.mappers.UserMapper;
import org.example.finalproject.entity.*;
import org.example.finalproject.exceptions.MissingInfoException;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.repository.TrainerTraineeRepository;
import org.example.finalproject.service.TrainerTraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerTraineeServiceImpl implements TrainerTraineeService {

    // Beans
    @Autowired
    private TrainerTraineeRepository trainerTraineeRepository;
    @Autowired
    private TrainerTraineeMapper trainerTraineeMapper;
    @Autowired
    private UserMapper userMapper;

    // Find trainer trainee by ID
    @Override
    public TrainerTraineeResponse findById(Integer id) {

        return trainerTraineeRepository.findById(id)
                .map(trainerTraineeMapper::toTrainerTraineeResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer Trainee not found with id: " + id));

    }

    // Get all trainer trainees
    @Override
    public List<TrainerTraineeResponse> getAllTrainerTrainees() {

        return trainerTraineeRepository.findAll().stream()
                .map(trainerTraineeMapper::toTrainerTraineeResponse)
                .toList();

    }

    // Create a new trainer trainee
    @Override
    @Transactional
    public void createTrainerTrainee(TrainerTraineeRequest request) {

        if (request.getTrainer() == null ||
                request.getTrainee() == null) {
            throw new MissingInfoException("One or more fields were not filled. Try again.");
        }

        // If the program stay in this point is because the request have all the attributes for create != null
        TrainerTrainee newTrainerTrainee = trainerTraineeMapper.toTrainerTrainee(request);

        trainerTraineeRepository.save(newTrainerTrainee);

    }

    // Update an existing trainer trainee
    @Override
    @Transactional
    public void updateTrainerTrainee(Integer id, TrainerTraineeRequest request) {

        TrainerTrainee existingTrainerTrainee = trainerTraineeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer Trainee not found with ID: " + id));

        if (request.getTrainer() != null)
            existingTrainerTrainee.setTrainer(userMapper.toUser(request.getTrainer()));

        if (request.getTrainee() != null)
            existingTrainerTrainee.setTrainee(userMapper.toUser(request.getTrainee()));

        trainerTraineeRepository.save(existingTrainerTrainee);

    }

    // Delete a trainer trainee
    @Override
    public void deleteTrainerTrainee(Integer id) {

        trainerTraineeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer Trainee not found with id: " + id));

        trainerTraineeRepository.deleteById(id);

    }

}
