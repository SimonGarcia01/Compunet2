package org.example.finalproject.service.impl;

import jakarta.transaction.Transactional;
import org.example.finalproject.api.v1.dtos.RecommendationRequest;
import org.example.finalproject.api.v1.dtos.RecommendationResponse;
import org.example.finalproject.api.v1.mappers.RecommendationMapper;
import org.example.finalproject.entity.GeneralProgress;
import org.example.finalproject.entity.Recommendation;
import org.example.finalproject.entity.User;
import org.example.finalproject.exceptions.MissingInfoException;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.repository.GeneralProgressRepository;
import org.example.finalproject.repository.RecommendationRepository;
import org.example.finalproject.repository.UserRepository;
import org.example.finalproject.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    // Beans
    @Autowired
    private RecommendationRepository recommendationRepository;
    @Autowired
    private RecommendationMapper recommendationMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GeneralProgressRepository generalProgressRepository;

    // Find recommendation by ID
    @Override
    public RecommendationResponse findById(Integer id) {
        return recommendationRepository.findById(id)
                .map(recommendationMapper::toRecommendationResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Recommendation not found with id: " + id));
    }

    // Get all recommendations
    @Override
    public List<RecommendationResponse> getAllRecommendations() {

        return recommendationRepository.findAll().stream()
                .map(recommendationMapper::toRecommendationResponse)
                .toList();

    }

    // Create a new recommendation
    @Override
    @Transactional
    public void createRecommendation(RecommendationRequest recommendationRequest) {

        if (recommendationRequest.getContent() == null || recommendationRequest.getContent().trim().isEmpty()) {
            throw new MissingInfoException("One or more fields were not filled. Try again.");
        }

        // Extraer el email del entrenador del SecurityContext (token JWT)
        String trainerEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        
        // Buscar el usuario entrenador existente por email
        User existingTrainer = userRepository.findByEmail(trainerEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found with email: " + trainerEmail));

        // Crear la recomendación
        Recommendation newRecommendation = new Recommendation();
        newRecommendation.setContent(recommendationRequest.getContent().trim());
        newRecommendation.setCommentDate(LocalDate.now());
        newRecommendation.setTrainer(existingTrainer);

        // Si se proporciona generalProgressId, buscar y asignar el GeneralProgress
        if (recommendationRequest.getGeneralProgressId() != null) {
            GeneralProgress generalProgress = generalProgressRepository.findById(recommendationRequest.getGeneralProgressId())
                    .orElseThrow(() -> new ResourceNotFoundException("General Progress not found with id: " + recommendationRequest.getGeneralProgressId()));
            newRecommendation.setGeneralProgress(generalProgress);
        }

        recommendationRepository.save(newRecommendation);

    }

    // Update an existing recommendation
    @Override
    @Transactional
    public void updateRecommendation(Integer id, RecommendationRequest recommendationRequest) {

        Recommendation recommendation = recommendationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recommendation not found with id: " + id));

        if (recommendationRequest.getContent() != null) {
            recommendation.setContent(recommendationRequest.getContent());
        }

        recommendationRepository.save(recommendation);

    }

    // Delete a recommendation
    @Override
    public void deleteRecommendation(Integer id) {

        recommendationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recommendation not found with id: " + id));

        recommendationRepository.deleteById(id);

    }

}