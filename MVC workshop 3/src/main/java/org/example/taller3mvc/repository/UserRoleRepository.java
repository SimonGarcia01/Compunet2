// UserRoleRepository.java
package org.example.taller3mvc.repository;

import org.example.taller3mvc.entity.UserRole;
import org.example.taller3mvc.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
    void deleteUserRoleById_UserId(Integer idUserId);
}
