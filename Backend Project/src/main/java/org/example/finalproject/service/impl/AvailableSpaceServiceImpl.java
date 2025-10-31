package org.example.finalproject.service.impl;

import jakarta.transaction.Transactional;
import org.example.finalproject.api.v1.dtos.AvailableSpaceRequest;
import org.example.finalproject.api.v1.dtos.AvailableSpaceResponse;
import org.example.finalproject.entity.AvailableSpace;
import org.example.finalproject.exceptions.MissingInfoException;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.exceptions.UniquenessViolationException;
import org.example.finalproject.api.v1.mappers.AvailableSpaceMapper;
import org.example.finalproject.repository.AvailableSpaceRepository;
import org.example.finalproject.service.AvailableSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailableSpaceServiceImpl implements AvailableSpaceService {

    @Autowired
    private AvailableSpaceRepository availableSpaceRepository;
    @Autowired
    private AvailableSpaceMapper availableSpaceMapper;

    // Get all spaces
    @Override
    public List<AvailableSpaceResponse> getAllAvailableSpaces() {
        return availableSpaceRepository.findAll().stream()
                .map(availableSpaceMapper::toAvailableSpaceResponse)
                .toList();
    }

    // Find space by ID
    @Override
    public AvailableSpaceResponse findById(Integer id) throws ResourceNotFoundException {
        return availableSpaceRepository.findById(id)
                .map(availableSpaceMapper::toAvailableSpaceResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Available space not found with id: " + id));
    }

    // Create a new available space
    @Override
    @Transactional
    public void createAvailableSpace(AvailableSpaceRequest availableSpaceRequest) {
        if (availableSpaceRequest.getName() == null ||
                availableSpaceRequest.getLocation() == null ||
                availableSpaceRequest.getLocationMaxAttendees() <= 0) {
            throw new MissingInfoException("One or more fields were not filled. Try again.");
        }

        // Check if a space with the same name already exists
        if (availableSpaceRepository.findByNameIgnoreCase(availableSpaceRequest.getName()).isPresent()) {
            throw new UniquenessViolationException("A space with this name already exists. Try another one.");
        }

        AvailableSpace newSpace = availableSpaceMapper.toAvailableSpace(availableSpaceRequest);

        availableSpaceRepository.save(newSpace);
    }

    // Update an existing available space
    @Override
    @Transactional
    public void updateAvailableSpace(Integer id, AvailableSpaceRequest availableSpaceRequest) {
        AvailableSpace existingSpace = availableSpaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Available space not found with id: " + id));

        // Update fields if provided
        if (availableSpaceRequest.getName() != null && availableSpaceRepository.findByNameIgnoreCase(availableSpaceRequest.getName()).isEmpty()) {
                existingSpace.setName(availableSpaceRequest.getName());
        } else {
            throw new UniquenessViolationException("A space with this name already exists.");
        }

        if (availableSpaceRequest.getLocation() != null) {
            existingSpace.setLocation(availableSpaceRequest.getLocation());
        }
        if (availableSpaceRequest.getLocationMaxAttendees() > 0) {
            existingSpace.setLocationMaxAttendees(availableSpaceRequest.getLocationMaxAttendees());
        }

        availableSpaceRepository.save(existingSpace);
    }

    // Delete an available space
    @Override
    public void deleteAvailableSpace(Integer id) {
        availableSpaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Available space not found with id: " + id));

        availableSpaceRepository.deleteById(id);
    }
}