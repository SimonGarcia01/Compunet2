package org.example.finalproject.service.impl;

import org.example.finalproject.api.v1.dtos.RolePrivilegeRequestResponse;
import org.example.finalproject.api.v1.dtos.RoleRequest;
import org.example.finalproject.api.v1.dtos.RoleResponse;
import org.example.finalproject.api.v1.mappers.PrivilegeMapper;
import org.example.finalproject.api.v1.mappers.RoleMapper;
import org.example.finalproject.api.v1.mappers.RolePrivilegeMapper;
import org.example.finalproject.entity.*;
import org.example.finalproject.exceptions.MissingInfoException;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.exceptions.UniquenessViolationException;
import org.example.finalproject.repository.PrivilegeRepository;
import org.example.finalproject.repository.RolePrivilegeRepository;
import org.example.finalproject.repository.RoleRepository;
import org.example.finalproject.service.RolePrivilegeService;
import org.example.finalproject.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RolePrivilegeServiceImpl implements RolePrivilegeService {

    @Autowired
    private RolePrivilegeRepository rolePrivilegeRepository;
    @Autowired
    private RolePrivilegeMapper rolePrivilegeMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PrivilegeMapper privilegeMapper;

    @Override
    public RolePrivilegeRequestResponse findById(Integer roleId, Integer privilegeId) {

        //Make the id so then it can be used to look for it
        RolePrivilegeId id = new RolePrivilegeId();
        id.setRoleId(roleId);
        id.setPrivilegeId(privilegeId);

        return rolePrivilegeRepository.findById(id)
                .map(rolePrivilegeMapper::toRolePrivilegeResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Role Privilege not found with id: " + id));
    }

    @Override
    public List<RolePrivilegeRequestResponse> getAllRolePrivileges() {
        return rolePrivilegeRepository.findAll().stream()
                .map(rolePrivilegeMapper::toRolePrivilegeResponse)
                .toList();
    }

    @Override
    @Transactional
    public void createRolePrivilege(RolePrivilegeRequestResponse request) {

        if (request.getRoleId() == null || request.getPrivilegeId() == null) {
            throw new MissingInfoException("One or more fields were not filled. Try again.");
        }

        RolePrivilege newRolePrivilege = rolePrivilegeMapper.toRolePrivilege(request);

        rolePrivilegeRepository.save(newRolePrivilege);

    }

    @Override
    @Transactional
    public void updateRolePrivilege(Integer roleId, Integer privilegeId, RolePrivilegeRequestResponse request) {

        //Make the id so then it can be used to look for it
        RolePrivilegeId id = new RolePrivilegeId();
        id.setRoleId(roleId);
        id.setPrivilegeId(privilegeId);

        RolePrivilege rolePrivilege = rolePrivilegeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role Privilege not found with id: " + id));

        if (request.getRole() != null) {
            rolePrivilege.setRole(roleMapper.toRole(request.getRole()));
        }

        if (request.getPrivilege() != null) {
            rolePrivilege.setPrivilege(privilegeMapper.toPrivilege(request.getPrivilege()));
        }

        rolePrivilegeRepository.save(rolePrivilege);

    }

    @Override
    public void deleteRolePrivilege(Integer roleId, Integer privilegeId) {

        //Make the id so then it can be used to look for it
        RolePrivilegeId id = new RolePrivilegeId();
        id.setRoleId(roleId);
        id.setPrivilegeId(privilegeId);

        if (!rolePrivilegeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Role Privilege not found with id: " + id);
        }
        rolePrivilegeRepository.deleteById(id);
    }

}
