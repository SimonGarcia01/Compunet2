package org.example.taller3mvc.service.impl;

import org.example.taller3mvc.entity.Privilege;
import org.example.taller3mvc.entity.Role;
import org.example.taller3mvc.entity.RolePrivilege;
import org.example.taller3mvc.entity.RolePrivilegeId;
import org.example.taller3mvc.repository.PrivilegeRepository;
import org.example.taller3mvc.repository.RolePrivilegeRepository;
import org.example.taller3mvc.repository.RoleRepository;
import org.example.taller3mvc.service.RoleService;
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

    @Override
    public long getCount() {
        return roleRepository.count();
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findById(Integer id) {
        return roleRepository.findById(id);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByNameIgnoreCase(name);
    }

    @Override
    @Transactional
    public Role createRole(Role role, List<Integer> privilegeIds) {
        if (privilegeIds == null || privilegeIds.isEmpty()) {
            throw new RuntimeException("Role must have at least one privilege");
        }
        Role saved = roleRepository.save(role);

        attachPrivileges(saved.getRoleId(), privilegeIds);

        return saved;
    }

    @Override
    @Transactional
    public Role updateRolePrivileges(Integer roleId, List<Integer> privilegeIds) {
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

        return role;
    }

    @Override
    public void deleteRole(Integer id) {
        roleRepository.deleteById(id);
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
}
