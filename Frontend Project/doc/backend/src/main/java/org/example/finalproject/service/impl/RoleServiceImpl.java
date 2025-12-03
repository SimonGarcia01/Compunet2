package org.example.finalproject.service.impl;

import org.example.finalproject.api.v1.dtos.RoleRequest;
import org.example.finalproject.api.v1.dtos.RoleResponse;
import org.example.finalproject.api.v1.mappers.RoleMapper;
import org.example.finalproject.entity.Privilege;
import org.example.finalproject.entity.Role;
import org.example.finalproject.entity.RolePrivilege;
import org.example.finalproject.entity.RolePrivilegeId;
import org.example.finalproject.exceptions.MissingInfoException;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.exceptions.UniquenessViolationException;
import org.example.finalproject.repository.PrivilegeRepository;
import org.example.finalproject.repository.RolePrivilegeRepository;
import org.example.finalproject.repository.RoleRepository;
import org.example.finalproject.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PrivilegeRepository privilegeRepository;
    @Autowired
    private RolePrivilegeRepository rolePrivilegeRepository;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public long getCount() {
        return roleRepository.count();
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    @Override
    public RoleResponse findById(Integer id) {
        return roleRepository.findById(id)
                .map(roleMapper::toRoleResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByNameIgnoreCase(name);
    }

    @Override
    @Transactional
    public void createRole(Role role, List<Integer> privilegeIds) {
        if (privilegeIds == null || privilegeIds.isEmpty()) {
            throw new RuntimeException("Role must have at least one privilege");
        }
        Role saved = roleRepository.save(role);

        attachPrivileges(saved.getRoleId(), privilegeIds);

    }

    @Override
    @Transactional
    public void updateRolePrivileges(Integer roleId, List<Integer> privilegeIds) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role with id " + roleId + " not found"));

        if (privilegeIds == null || privilegeIds.isEmpty()) {
            throw new RuntimeException("Role must have at least one privilege");
        }

        // Limpiar actuales
        rolePrivilegeRepository.deleteAll(
                rolePrivilegeRepository.findAll().stream()
                        .filter(rp -> rp.getRolePrivilegeId().getRoleId().equals(roleId))
                        .toList()
        );

        attachPrivileges(roleId, privilegeIds);

    }

    @Override
    @Transactional
    public void createRole(RoleRequest request) {

        if (request == null || request.getName() == null || request.getName().isBlank()) {
            throw new MissingInfoException("One or more fields were not filled. Try again.");
        }

        String name = request.getName().trim();
        if (roleRepository.existsByNameIgnoreCase(name)) {
            throw new UniquenessViolationException("Another role with this name already exists");
        }

        Role newRole = roleMapper.toRole(request);

        roleRepository.save(newRole);

    }

    @Override
    @Transactional
    public void updateRole(Integer id, RoleRequest changes) {

        Role current = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));

        // Name
        if (changes.getName() != null && !changes.getName().isBlank()) {
            String newName = changes.getName().trim();
            if (!current.getName().equalsIgnoreCase(newName)
                    && roleRepository.existsByNameIgnoreCase(newName)) {
                throw new UniquenessViolationException("Another role with this name already exists");
            }
            current.setName(newName);
        }
        // Description
        if (changes.getDescription() != null) {
            current.setDescription(changes.getDescription().trim());
        }

        roleRepository.save(current);

    }

    private void attachPrivileges(Integer roleId, List<Integer> privilegeIds) {
        for (Integer pid : privilegeIds) {
            Privilege p = privilegeRepository.findById(pid)
                    .orElseThrow(() -> new RuntimeException("Privilege with id " + pid + " not found"));

            RolePrivilegeId id = new RolePrivilegeId();
            id.setRoleId(roleId);
            id.setPrivilegeId(p.getPrivilegeId());

            RolePrivilege link = new RolePrivilege();
            link.setRolePrivilegeId(id);
            // Si tu entidad tiene @MapsId, setea también las refs:
            link.setRole(roleRepository.getReferenceById(roleId));
            link.setPrivilege(p);

            rolePrivilegeRepository.save(link);
        }
    }

    @Override
    public void deleteRole(Integer id) {

        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Role not found with id: " + id);
        }
        roleRepository.deleteById(id);
    }

}
