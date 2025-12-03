package org.example.finalproject.api.v1.mappers;

import org.example.finalproject.api.v1.dtos.GeneralProgressRequest;
import org.example.finalproject.api.v1.dtos.GeneralProgressResponse;
import org.example.finalproject.entity.GeneralProgress;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GeneralProgressMapper {

    public GeneralProgress toGeneralProgress(GeneralProgressRequest generalProgressRequest);
    public GeneralProgressResponse toGeneralProgressResponse(GeneralProgress generalProgress);

}
