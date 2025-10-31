// RolePrivilegeRepository.java
package org.example.taller3mvc.repository;

import org.example.taller3mvc.entity.RolePrivilege;
import org.example.taller3mvc.entity.RolePrivilegeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePrivilegeRepository extends JpaRepository<RolePrivilege, RolePrivilegeId> {}
