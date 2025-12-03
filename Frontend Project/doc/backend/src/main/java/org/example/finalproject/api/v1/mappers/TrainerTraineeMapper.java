package org.example.finalproject.api.v1.mappers;

import org.example.finalproject.api.v1.dtos.TrainerTraineeRequest;
import org.example.finalproject.api.v1.dtos.TrainerTraineeResponse;
import org.example.finalproject.entity.TrainerTrainee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrainerTraineeMapper {

    public TrainerTrainee toTrainerTrainee(TrainerTraineeRequest trainerTraineeRequest);
    public TrainerTraineeResponse toTrainerTraineeResponse(TrainerTrainee trainerTrainee);

}
