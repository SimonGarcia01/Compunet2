package org.example.finalproject.service;

import org.example.finalproject.api.v1.dtos.HistoricalRecordRequest;
import org.example.finalproject.api.v1.dtos.HistoricalRecordResponse;
import org.example.finalproject.api.v1.dtos.PrivilegeRequest;
import org.example.finalproject.api.v1.dtos.PrivilegeResponse;
import org.example.finalproject.entity.Privilege;

import java.util.List;
import java.util.Optional;

public interface PrivilegeService {
    long getCount();
    PrivilegeResponse findById(Integer id);
    List<PrivilegeResponse> getAllPrivileges();
    PrivilegeResponse findByName(String name);
    void createPrivilege(PrivilegeRequest privilegeRequest);
    void updatePrivilege(Integer id, PrivilegeRequest privilegeRequest);  // <-- nuevo
    void deletePrivilege(Integer id);

}
