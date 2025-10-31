// UserRepository.java
package org.example.taller3mvc.repository;

import org.example.taller3mvc.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByPersonalId(String personalId); // si aún lo usas en otras partes
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    // --- NUEVOS MÉTODOS para evitar LazyInitializationException ---

    /**
     * Carga al usuario junto con roles y privilegios usando @EntityGraph.
     */
    @EntityGraph(attributePaths = {
            "userRolesList",
            "userRolesList.role",
            "userRolesList.role.rolePrivilegesList",
            "userRolesList.role.rolePrivilegesList.privilege"
    })
    Optional<User> findOneWithRolesAndPrivilegesByEmailIgnoreCase(String email);

    /**
     * Alternativa usando JPQL con FETCH JOIN, útil si prefieres mayor control.
     */
    @Query("""
        select distinct u from User u
          left join fetch u.userRolesList ur
          left join fetch ur.role r
          left join fetch r.rolePrivilegesList rp
          left join fetch rp.privilege p
        where lower(u.email) = lower(:email)
    """)
    Optional<User> findOneFetchByEmail(@Param("email") String email);
}