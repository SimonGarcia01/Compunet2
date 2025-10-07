package org.example.taller2springboot.service;

import org.example.taller2springboot.entity.Privilege;

import java.util.List;
import java.util.Optional;

public interface PrivilegeService {

    long getCount();

    List<Privilege> getPrivileges();

    Optional<Privilege> findById(Integer id);

    Optional<Privilege> findByName(String name);

    Privilege createPrivilege(Privilege privilege);

    void deletePrivilege(Integer id);
}
