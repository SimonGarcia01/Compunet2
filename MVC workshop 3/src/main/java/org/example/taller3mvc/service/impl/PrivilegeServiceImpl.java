package org.example.taller3mvc.service.impl;

import jakarta.transaction.Transactional;
import org.example.taller3mvc.entity.Privilege;
import org.example.taller3mvc.repository.PrivilegeRepository;
import org.example.taller3mvc.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Override
    public long getCount() { return privilegeRepository.count(); }

    @Override
    public List<Privilege> getPrivileges() { return privilegeRepository.findAll(); }

    @Override
    public Optional<Privilege> findById(Integer id) { return privilegeRepository.findById(id); }

    @Override
    public Optional<Privilege> findByName(String name) {
        return (name == null) ? Optional.empty()
                : privilegeRepository.findByNameIgnoreCase(name.trim());
    }

    @Override
    @Transactional
    public Privilege createPrivilege(Privilege privilege) {
        if (privilege == null || privilege.getName() == null || privilege.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre del privilegio es obligatorio");
        }
        String name = privilege.getName().trim();
        if (privilegeRepository.existsByNameIgnoreCase(name)) {
            throw new IllegalArgumentException("Ya existe un privilegio con ese nombre");
        }
        privilege.setName(name);
        // (opcional) normaliza descripción
        if (privilege.getDescription() != null) {
            privilege.setDescription(privilege.getDescription().trim());
        }
        return privilegeRepository.save(privilege);
    }

    @Override
    @Transactional
    public Privilege updatePrivilege(Integer id, Privilege changes) {
        Privilege current = privilegeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Privilegio no encontrado: " + id));

        // nombre
        if (changes.getName() != null && !changes.getName().isBlank()) {
            String newName = changes.getName().trim();
            if (!current.getName().equalsIgnoreCase(newName)
                    && privilegeRepository.existsByNameIgnoreCase(newName)) {
                throw new IllegalArgumentException("Ya existe un privilegio con ese nombre");
            }
            current.setName(newName);
        }
        // descripción
        if (changes.getDescription() != null) {
            current.setDescription(changes.getDescription().trim());
        }
        return privilegeRepository.save(current);
    }

    @Override
    @Transactional
    public void deletePrivilege(Integer id) {
        if (!privilegeRepository.existsById(id)) {
            throw new IllegalArgumentException("Privilegio no encontrado: " + id);
        }
        privilegeRepository.deleteById(id);
    }
}
