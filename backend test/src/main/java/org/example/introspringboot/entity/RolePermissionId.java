package org.example.introspringboot.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RolePermissionId implements Serializable {
    private Long roleId;
    private Long permissionId;

    public RolePermissionId() {}

    public RolePermissionId(Long roleId, Long permissionId) {
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    // equals y hashCode obligatorios
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RolePermissionId)) return false;
        RolePermissionId that = (RolePermissionId) o;
        return Objects.equals(roleId, that.roleId) &&
                Objects.equals(permissionId, that.permissionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, permissionId);
    }
}
