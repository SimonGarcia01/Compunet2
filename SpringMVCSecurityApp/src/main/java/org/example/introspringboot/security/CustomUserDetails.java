package org.example.introspringboot.security;

import org.example.introspringboot.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private User userDB;

    //The UserDetails is created from a User from the DB
    public CustomUserDetails(User user) {
        this.userDB = user;
    }

    @Override
    public String getPassword() {
        return userDB.getPassword();
    }

    @Override
    public String getUsername() {
        return userDB.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_USER");

        //Map
        //This var represents List<SimpleGrantedAuthority>
        //Use a .map() to go through the list of strings and make them authorities
        var rolesAuthorities = userDB.getUserRoles().stream().map(
                userRole -> new SimpleGrantedAuthority(userRole.getRole().getName())
        ).toList();

        //Now to access the privileges
        //List<Role>
        var rolesOfUser = userDB.getUserRoles().stream().map(userRole -> userRole.getRole()).toList();

        //Now go through each role and get the RolePermission to finally get the permissions
        //List<Permission>
        var permissions = rolesOfUser.stream().
                flatMap(role -> role.getRolePermissions().stream()).
                map(rolePermission -> rolePermission.getPermission()).toList();

        //Make every permission into a Simple granted Authority
        //List<SimpleGrantedAuthority>
        var permissionAuthorities = permissions.stream().map(
                permission -> new SimpleGrantedAuthority(permission.getName())
        ).toList();

        //Add all Simple Granted Authorities into one list
        var fullAuthorities = new ArrayList<GrantedAuthority>();
        fullAuthorities.addAll(rolesAuthorities);
        fullAuthorities.addAll(permissionAuthorities);

        return fullAuthorities;
    }
}
