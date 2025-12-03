package org.example.finalproject.service.impl;

import jakarta.transaction.Transactional;
import org.example.finalproject.api.v1.dtos.HistoricalRecordRequest;
import org.example.finalproject.api.v1.dtos.HistoricalRecordResponse;
import org.example.finalproject.api.v1.mappers.EventMapper;
import org.example.finalproject.api.v1.mappers.HistoricalRecordMapper;
import org.example.finalproject.api.v1.mappers.UserMapper;
import org.example.finalproject.api.v1.mappers.WorkoutProgramMapper;
import org.example.finalproject.entity.HistoricalRecord;
import org.example.finalproject.exceptions.MissingInfoException;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.repository.HistoricalRecordRepository;
import org.example.finalproject.service.HistoricalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class HistoricalRecordServiceImpl implements  HistoricalRecordService {

    // Beans
    @Autowired
    private HistoricalRecordRepository historicalRecordRepository;
    @Autowired
    private HistoricalRecordMapper historicalRecordMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WorkoutProgramMapper workoutProgramMapper;
    @Autowired
    private EventMapper eventMapper;

    // Find historical record by ID
    @Override
    public HistoricalRecordResponse findById(Integer id) {
        return historicalRecordRepository.findById(id)
                .map(historicalRecordMapper::toHistoricalRecordResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Historical Record not found with id: " + id));
    }

    // Get all historical records
    @Override
    public List<HistoricalRecordResponse> getAllHistoricalRecords() {

        return historicalRecordRepository.findAll().stream()
                .map(historicalRecordMapper::toHistoricalRecordResponse)
                .toList();

    }

    // Create a new exercise
    @Override
    @Transactional
    public void createHistoricalRecord(HistoricalRecordRequest request) {

        if (request .getUser() == null ||
                request.getEvent() == null ||
                request.getDetails() == null ||
                request.getEstimatedBurntCalories() == null) {
            throw new MissingInfoException("One or more fields were not filled. Try again.");
        }

        // If the program stay in this point is because the request have all the attributes for create != null
        HistoricalRecord newHistoricalRecord = historicalRecordMapper.toHistoricalRecord(request);

        historicalRecordRepository.save(newHistoricalRecord);

    }

    // Update an existing historical record
    @Override
    @Transactional
    public void updateHistoricalRecord(Integer id, HistoricalRecordRequest request) {

        HistoricalRecord historicalRecord = historicalRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Historical Record not found with id: " + id));

        if (request.getUser() != null) {
            historicalRecord.setUser(userMapper.toUser(request.getUser()));
        }

        if(request.getEvent() != null) {
            historicalRecord.setEvent(eventMapper.toEvent(request.getEvent()));
        }

        if(request.getWorkoutProgram() != null) {
            historicalRecord.setWorkoutProgram(workoutProgramMapper.toWorkoutProgram(request.getWorkoutProgram()));
        }

        if(request.getDetails() != null) {
            historicalRecord.setDetails(request.getDetails());
        }

        // compare To == 1 means that the first parameter is greater than the second parameter
        if(request.getEstimatedBurntCalories().compareTo(BigDecimal.valueOf(0)) == 1) {
            historicalRecord.setEstimatedBurntCalories(request.getEstimatedBurntCalories());
        }

        historicalRecordRepository.save(historicalRecord);

    }

    // Delete a historical record
    @Override
    public void deleteHistoricalRecord(Integer id) {

        historicalRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Historical Record not found with id: " + id));

        historicalRecordRepository.deleteById(id);

    }

}
