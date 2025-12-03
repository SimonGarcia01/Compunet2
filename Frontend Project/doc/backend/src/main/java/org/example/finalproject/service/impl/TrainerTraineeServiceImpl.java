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
import org.example.finalproject.repository.UserRepository;
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
    @Autowired
    private UserRepository userRepository;

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
                request.getTrainee() == null ||
                request.getTrainer().getEmail() == null ||
                request.getTrainee().getEmail() == null) {
            throw new MissingInfoException("One or more fields were not filled. Try again.");
        }

        // Buscar los usuarios existentes por email en lugar de crear nuevos
        // Esto evita el error HHH000437: Attempting to save unsaved transient entity
        User existingTrainer = userRepository.findByEmail(request.getTrainer().getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found with email: " + request.getTrainer().getEmail()));

        User existingTrainee = userRepository.findByEmail(request.getTrainee().getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Trainee not found with email: " + request.getTrainee().getEmail()));

        // Crear la relación usando los usuarios existentes
        TrainerTrainee newTrainerTrainee = trainerTraineeMapper.toTrainerTrainee(request);
        
        // Asignar los usuarios existentes (no los nuevos creados por MapStruct)
        newTrainerTrainee.setTrainer(existingTrainer);
        newTrainerTrainee.setTrainee(existingTrainee);

        trainerTraineeRepository.save(newTrainerTrainee);

    }

    // Update an existing trainer trainee
    @Override
    @Transactional
    public void updateTrainerTrainee(Integer id, TrainerTraineeRequest request) {

        TrainerTrainee existingTrainerTrainee = trainerTraineeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer Trainee not found with ID: " + id));

        // Buscar usuarios existentes por email en lugar de crear nuevos
        if (request.getTrainer() != null && request.getTrainer().getEmail() != null) {
            User existingTrainer = userRepository.findByEmail(request.getTrainer().getEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("Trainer not found with email: " + request.getTrainer().getEmail()));
            existingTrainerTrainee.setTrainer(existingTrainer);
        }

        if (request.getTrainee() != null && request.getTrainee().getEmail() != null) {
            User existingTrainee = userRepository.findByEmail(request.getTrainee().getEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("Trainee not found with email: " + request.getTrainee().getEmail()));
            existingTrainerTrainee.setTrainee(existingTrainee);
        }

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
