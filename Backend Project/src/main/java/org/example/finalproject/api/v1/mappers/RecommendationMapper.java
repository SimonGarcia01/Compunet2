package org.example.finalproject.api.v1.mappers;

import org.example.finalproject.api.v1.dtos.RecommendationRequest;
import org.example.finalproject.api.v1.dtos.RecommendationResponse;
import org.example.finalproject.entity.Recommendation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecommendationMapper {

    public Recommendation toRecommendation(RecommendationRequest recommendationRequest);
    public RecommendationResponse toRecommendationResponse(Recommendation recommendation);

}
