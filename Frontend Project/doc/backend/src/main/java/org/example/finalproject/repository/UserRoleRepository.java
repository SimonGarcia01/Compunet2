// UserRoleRepository.java
package org.example.finalproject.repository;

import org.example.finalproject.entity.UserRole;
import org.example.finalproject.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
    void deleteUserRoleById_UserId(Integer idUserId);
}
