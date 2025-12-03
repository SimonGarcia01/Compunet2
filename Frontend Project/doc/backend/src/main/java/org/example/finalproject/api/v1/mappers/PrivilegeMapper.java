package org.example.finalproject.api.v1.mappers;

import org.example.finalproject.api.v1.dtos.PrivilegeRequest;
import org.example.finalproject.api.v1.dtos.PrivilegeResponse;
import org.example.finalproject.entity.Privilege;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrivilegeMapper {

    public Privilege toPrivilege(PrivilegeRequest privilegeRequest);
    public PrivilegeResponse toPrivilegeResponse(Privilege privilege);

}
