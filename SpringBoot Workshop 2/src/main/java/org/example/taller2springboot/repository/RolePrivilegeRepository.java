// RolePrivilegeRepository.java
package org.example.taller2springboot.repository;

import org.example.taller2springboot.entity.RolePrivilege;
import org.example.taller2springboot.entity.RolePrivilegeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePrivilegeRepository extends JpaRepository<RolePrivilege, RolePrivilegeId> {}
