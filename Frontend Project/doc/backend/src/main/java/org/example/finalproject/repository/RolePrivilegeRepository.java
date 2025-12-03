// RolePrivilegeRepository.java
package org.example.finalproject.repository;

import org.example.finalproject.entity.RolePrivilege;
import org.example.finalproject.entity.RolePrivilegeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePrivilegeRepository extends JpaRepository<RolePrivilege, RolePrivilegeId> {}
