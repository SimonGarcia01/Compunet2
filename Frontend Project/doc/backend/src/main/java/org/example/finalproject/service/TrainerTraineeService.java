package org.example.finalproject.service;

import org.example.finalproject.api.v1.dtos.TrainerTraineeRequest;
import org.example.finalproject.api.v1.dtos.TrainerTraineeResponse;

import java.util.List;

public interface TrainerTraineeService {

    TrainerTraineeResponse findById(Integer id);
    List<TrainerTraineeResponse> getAllTrainerTrainees();
    void createTrainerTrainee(TrainerTraineeRequest trainerTraineeRequest);
    void updateTrainerTrainee(Integer id, TrainerTraineeRequest trainerTraineeRequest);
    void deleteTrainerTrainee(Integer id);

}
