package org.example.finalproject.service.impl;

import jakarta.transaction.Transactional;
import org.example.finalproject.api.v1.dtos.PrivilegeRequest;
import org.example.finalproject.api.v1.dtos.PrivilegeResponse;
import org.example.finalproject.api.v1.mappers.PrivilegeMapper;
import org.example.finalproject.entity.Notification;
import org.example.finalproject.entity.Privilege;
import org.example.finalproject.exceptions.MissingInfoException;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.exceptions.UniquenessViolationException;
import org.example.finalproject.repository.PrivilegeRepository;
import org.example.finalproject.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    // Beans
    @Autowired
    private PrivilegeRepository privilegeRepository;
    @Autowired
    private PrivilegeMapper privilegeMapper;


    @Override
    public long getCount() {
        return privilegeRepository.count();
    }

    // Find privilege by ID
    @Override
    public PrivilegeResponse findById(Integer id) {
        return privilegeRepository.findById(id)
                .map(privilegeMapper::toPrivilegeResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Privilege not found with id: " + id));
    }

    // Get all privileges
    @Override
    public List<PrivilegeResponse> getAllPrivileges() {

        return privilegeRepository.findAll().stream()
                .map(privilegeMapper::toPrivilegeResponse)
                .toList();

    }

    @Override
    public PrivilegeResponse findByName(String name) {

        return privilegeRepository.findByNameIgnoreCase(name.trim()).map(privilegeMapper::toPrivilegeResponse)
                    .orElseThrow(() -> new ResourceNotFoundException("Privilege not found with name: " + name));

    }


    // Create a new privilege
    @Override
    @Transactional
    public void createPrivilege(PrivilegeRequest request) {

        if (request == null || request.getName() == null || request.getName().isBlank()) {
            throw new MissingInfoException("One or more fields were not filled. Try again.");
        }

        String name = request.getName().trim();
        if (privilegeRepository.existsByNameIgnoreCase(name)) {
            throw new UniquenessViolationException("Another privilege with this name already exists");
        }

        Privilege newPrivilege = privilegeMapper.toPrivilege(request);

        privilegeRepository.save(newPrivilege);

    }

    // Update an existing notification
    @Override
    @Transactional
    public void updatePrivilege(Integer id, PrivilegeRequest changes) {

        Privilege current = privilegeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Privilege not found with id: " + id));

        // Name
        if (changes.getName() != null && !changes.getName().isBlank()) {
            String newName = changes.getName().trim();
            if (!current.getName().equalsIgnoreCase(newName)
                    && privilegeRepository.existsByNameIgnoreCase(newName)) {
                throw new UniquenessViolationException("Another privilege with this name already exists");
            }
            current.setName(newName);
        }
        // Description
        if (changes.getDescription() != null) {
            current.setDescription(changes.getDescription().trim());
        }

        privilegeRepository.save(current);

    }

    // Delete a privilege
    @Override
    @Transactional
    public void deletePrivilege(Integer id) {

        if (!privilegeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Privilege not found with id: " + id);
        }
        privilegeRepository.deleteById(id);

    }
}
