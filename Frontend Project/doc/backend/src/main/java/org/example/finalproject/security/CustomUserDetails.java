
package org.example.finalproject.security;

import org.example.finalproject.entity.RolePrivilege;
import org.example.finalproject.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final User userDB;

    public CustomUserDetails(User user) {
        this.userDB = user;
    }

    @Override public String getPassword() { return userDB.getEncryptedPassword(); }
    @Override public String getUsername() { return userDB.getEmail(); }
    @Override public boolean isEnabled() { return userDB.getActive(); }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var rolesAuthorities = userDB.getUserRolesList().stream()
                .map(ur -> new SimpleGrantedAuthority(ur.getRole().getName()))
                .toList();

        var permissions = userDB.getUserRolesList().stream()
                .map(ur -> ur.getRole())
                .flatMap(r -> r.getRolePrivilegesList().stream())
                .map(RolePrivilege::getPrivilege)
                .map(p -> new SimpleGrantedAuthority(p.getName()))
                .toList();

        var all = new ArrayList<GrantedAuthority>(rolesAuthorities.size() + permissions.size());
        all.addAll(rolesAuthorities);
        all.addAll(permissions);
        return all;
    }
}