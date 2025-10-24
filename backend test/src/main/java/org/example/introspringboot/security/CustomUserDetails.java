package org.example.introspringboot.security;

import org.example.introspringboot.entity.Permission;
import org.example.introspringboot.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private User user;
    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //MAP
        var rolesAuthorities = user.getUserRoles().stream().map(
                userRole -> new SimpleGrantedAuthority(userRole.getRole().getName())
        ).toList();

        var rolesOfUser = user.getUserRoles().stream()
                .map(
                        userRole -> userRole.getRole()
                ).toList();

        var permissions = rolesOfUser.stream()
                .flatMap(role -> role.getRolePermissions().stream())
                .map(rolePermission -> rolePermission.getPermission()).toList();

        var permissionAuthorities = permissions.stream().map(
                permission -> new SimpleGrantedAuthority(permission.getName())
        ).toList();

        var fullAuthorities = new ArrayList<GrantedAuthority>();
        fullAuthorities.addAll(rolesAuthorities);
        fullAuthorities.addAll(permissionAuthorities);

        return fullAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
}
