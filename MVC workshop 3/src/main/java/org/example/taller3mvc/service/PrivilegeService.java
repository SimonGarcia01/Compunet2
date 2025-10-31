package org.example.taller3mvc.service;

import org.example.taller3mvc.entity.Privilege;

import java.util.List;
import java.util.Optional;

public interface PrivilegeService {
    long getCount();
    List<Privilege> getPrivileges();
    Optional<Privilege> findById(Integer id);
    Optional<Privilege> findByName(String name);
    Privilege createPrivilege(Privilege privilege);
    Privilege updatePrivilege(Integer id, Privilege privilege);  // <-- nuevo
    void deletePrivilege(Integer id);
}
