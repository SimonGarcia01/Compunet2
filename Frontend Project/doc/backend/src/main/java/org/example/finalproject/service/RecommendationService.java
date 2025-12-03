package org.example.finalproject.service;

import org.example.finalproject.api.v1.dtos.RecommendationRequest;
import org.example.finalproject.api.v1.dtos.RecommendationResponse;

import java.util.List;

public interface RecommendationService {

    RecommendationResponse findById(Integer id);
    List<RecommendationResponse> getAllRecommendations();
    void createRecommendation(RecommendationRequest recommendationRequest);
    void updateRecommendation(Integer id, RecommendationRequest recommendationRequest);
    void deleteRecommendation(Integer id);

}
