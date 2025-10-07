// UserRoleRepository.java
package org.example.taller2springboot.repository;

import org.example.taller2springboot.entity.UserRole;
import org.example.taller2springboot.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
    void deleteUserRoleById_UserId(Integer idUserId);
}
