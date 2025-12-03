package org.example.finalproject.service.impl;

import jakarta.transaction.Transactional;
import org.example.finalproject.api.v1.dtos.GeneralProgressRequest;
import org.example.finalproject.api.v1.dtos.GeneralProgressResponse;
import org.example.finalproject.api.v1.mappers.GeneralProgressMapper;
import org.example.finalproject.entity.GeneralProgress;
import org.example.finalproject.exceptions.MissingInfoException;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.repository.GeneralProgressRepository;
import org.example.finalproject.service.GeneralProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class GeneralProgressServiceImpl implements GeneralProgressService {

    // Beans
    @Autowired
    private GeneralProgressRepository generalProgressRepository;
    @Autowired
    private GeneralProgressMapper generalProgressMapper;

    // Find general progress by ID
    @Override
    public GeneralProgressResponse findById(Integer id) {
        return generalProgressRepository.findById(id)
                .map(generalProgressMapper::toGeneralProgressResponse)
                .orElseThrow(() -> new ResourceNotFoundException("General Progress not found with id: " + id));
    }

    // Get all general progress
    @Override
    public List<GeneralProgressResponse> getAllGeneralProgress() {

        return generalProgressRepository.findAll().stream()
                .map(generalProgressMapper::toGeneralProgressResponse)
                .toList();

    }

    // Create a new general progress
    @Override
    @Transactional
    public void createGeneralProgress(GeneralProgressRequest request) {

        if (request.getType() == null ||
                request.getPercentage() == null ||
                request.getDays_or_weeks() == null) {
            throw new MissingInfoException("One or more fields were not filled. Try again.");
        }

        // If the program stay in this point is because the request have all the attributes for create != null
        GeneralProgress newGeneralProgress = generalProgressMapper.toGeneralProgress(request);

        generalProgressRepository.save(newGeneralProgress);

    }

    // Update an existing general progress
    @Override
    @Transactional
    public void updateGeneralProgress(Integer id, GeneralProgressRequest request) {

        GeneralProgress generalProgress = generalProgressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("General Progress not found with id: " + id));

        // Update fields if provided
        if (request.getType() != null) {
            generalProgress.setType(request.getType());
        }

        // compare To == 1 means that the first parameter is greater than the second parameter
        if(request.getPercentage().compareTo(BigDecimal.valueOf(0)) == 1) {
            generalProgress.setPercentage(request.getPercentage());
        }

        if(request.getDays_or_weeks() != null) {
            generalProgress.setDays_or_weeks(request.getDays_or_weeks());
        }

        generalProgressRepository.save(generalProgress);

    }

    @Override
    public void deleteGeneralProgress(Integer id) {

        generalProgressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("General Progress not found with id: " + id));

        generalProgressRepository.deleteById(id);

    }

}
