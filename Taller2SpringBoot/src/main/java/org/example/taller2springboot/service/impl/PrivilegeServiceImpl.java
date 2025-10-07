package org.example.taller2springboot.service.impl;

import org.example.taller2springboot.entity.Privilege;
import org.example.taller2springboot.repository.PrivilegeRepository;
import org.example.taller2springboot.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Override
    public long getCount() {
        return privilegeRepository.count();
    }

    @Override
    public List<Privilege> getPrivileges() {
        return privilegeRepository.findAll();
    }

    @Override
    public Optional<Privilege> findById(Integer id) {
        return privilegeRepository.findById(id);
    }

    @Override
    public Optional<Privilege> findByName(String name) {
        return privilegeRepository.findByNameIgnoreCase(name);
    }

    @Override
    public Privilege createPrivilege(Privilege privilege) {
        return privilegeRepository.save(privilege);
    }

    @Override
    public void deletePrivilege(Integer id) {
        privilegeRepository.deleteById(id);
    }
}
