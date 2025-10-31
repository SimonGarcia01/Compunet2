package org.example.finalproject.api.v1.mappers;

import org.example.finalproject.api.v1.dtos.UserWorkoutProgramRequestResponse;
import org.example.finalproject.entity.UserWorkoutProgram;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserWorkoutProgramMapper {

    public UserWorkoutProgram toUserWorkoutProgram(UserWorkoutProgramRequestResponse userWorkoutProgramRequest);
    public UserWorkoutProgramRequestResponse toUserWorkoutProgramResponse(UserWorkoutProgram userWorkoutProgram);

}
