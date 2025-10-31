package org.example.finalproject.service;

import org.example.finalproject.api.v1.dtos.AvailableSpaceRequest;
import org.example.finalproject.api.v1.dtos.AvailableSpaceResponse;

public interface AvailableSpaceService {

    AvailableSpaceResponse findById(Integer id);
    Object getAllAvailableSpaces();
    void createAvailableSpace(AvailableSpaceRequest availableSpaceRequest);
    void updateAvailableSpace(Integer id, AvailableSpaceRequest availableSpaceRequest);
    void deleteAvailableSpace(Integer id);

}
