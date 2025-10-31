// CustomUserDetailService.java
package org.example.taller3mvc.security;

import org.example.taller3mvc.entity.User;
import org.example.taller3mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    /**
     * username del form = email
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("[AUTH] Intentando autenticar email: " + username);
        var user = userService.findByEmailWithAuthorities(username)
                .orElseThrow(() -> {
                    System.out.println("[AUTH] ❌ Usuario NO encontrado: " + username);
                    return new UsernameNotFoundException("No existe usuario con email: " + username);
                });
        System.out.println("[AUTH] ✅ Usuario encontrado: " + user.getEmail() +
                " | roles=" + (user.getUserRolesList()!=null ? user.getUserRolesList().size() : 0));
        return new CustomUserDetails(user);
    }
}